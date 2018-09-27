package com.test.foodappchallenge.fooddetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import com.jakewharton.rxbinding2.view.RxView
import com.squareup.picasso.Picasso
import com.test.foodappchallenge.CircleTransformation
import com.test.foodappchallenge.R
import com.test.foodappchallenge.domain.model.Food
import com.test.foodappchallenge.fooddetail.di.DaggerFoodDetailComponent
import com.test.foodappchallenge.fooddetail.di.FoodDetailModule
import kotlinx.android.synthetic.main.activity_food_detail.*
import kotlinx.android.synthetic.main.layout_content_user.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FoodDetailActivity : AppCompatActivity(), FoodDetailContract.View {

    @Inject
    lateinit var presenter: FoodDetailContract.Presenter

    private val collapsingTitleTrigger = 0.65

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)
        injectDependencies()
        startAnimations()
        setUpViews()

        intent.extras?.let {
            handleExtras(it)
        }
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }

    @SuppressLint("RestrictedApi")
    override fun supportFinishAfterTransition() {
        fabFavorite.visibility = View.GONE
        super.supportFinishAfterTransition()
    }

    override fun setUserPicture(image: String) {
        Picasso.get()
                .load(getDrawableId(image))
                .transform(CircleTransformation())
                .into(ivUserPicture)
    }

    override fun setUserName(name: String?) {
        tvUserName.text = name
    }

    override fun setDateTime(dateTime: String?) {
        tvFoodDateTime.text = dateTime
    }

    override fun setDescription(description: String?) {
        tvFoodDescription.text = description
    }

    override fun setFavoriteNumber(number: Int?) {
        tvFoodFavCount.text = number?.toString()
    }

    override fun setFoodPicture(image: String) {
        Picasso.get()
                .load(getDrawableId(image))
                .into(ivFoodPicture)
    }

    override fun setFavoriteOnOff(param: Boolean) {
        fabFavorite.setImageResource(
                if (param)
                    R.drawable.ic_favorite_on
                else
                    R.drawable.ic_favorite_off
        )
    }

    override fun showError(error: String?) {
        Snackbar.make(screenContent,
                error ?: getText(R.string.unknown_error),
                Snackbar.LENGTH_LONG).show()
    }

    private fun injectDependencies() {
        DaggerFoodDetailComponent
                .builder()
                .foodDetailModule(FoodDetailModule(this))
                .build()
                .inject(this)
    }

    private fun startAnimations() {
        fabFavorite.show()
        tvFoodDescription.startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.slide_up))
    }

    private fun setUpViews() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            this.setDisplayHomeAsUpEnabled(true)
            this.setDisplayShowHomeEnabled(true)
        }

        appBar.addOnOffsetChangedListener(
                AppBarLayout.OnOffsetChangedListener { _, offSet ->
                    supportActionBar?.let {
                        val triggerPercent = appBar.totalScrollRange * collapsingTitleTrigger
                        if (Math.abs(offSet) >= (triggerPercent)) {
                            collapsing.isTitleEnabled = true
                            it.setDisplayShowTitleEnabled(true)
                        } else {
                            collapsing.isTitleEnabled = false
                            it.setDisplayShowTitleEnabled(false)
                        }
                    }
                })

        RxView.clicks(fabFavorite)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    presenter.favoriteFood()
                }
    }

    private fun handleExtras(extras: Bundle) {
        val food = extras.getSerializable(Food::class.java.name) as Food
        presenter.loadScreen(food)
    }

    private fun getDrawableId(key: String): Int {
        return resources
                .getIdentifier(key.toLowerCase(), "drawable", packageName)
    }
}
