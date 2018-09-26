package com.test.foodappchallenge.fooddetail

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.animation.AnimationUtils
import com.squareup.picasso.Picasso
import com.test.foodappchallenge.CircleTransformation
import com.test.foodappchallenge.R
import com.test.foodappchallenge.domain.model.Food
import com.test.foodappchallenge.fooddetail.di.DaggerFoodDetailComponent
import com.test.foodappchallenge.fooddetail.di.FoodDetailModule
import kotlinx.android.synthetic.main.activity_food_detail.*
import kotlinx.android.synthetic.main.layout_content_user.*
import javax.inject.Inject

class FoodDetailActivity : AppCompatActivity(), FoodDetailContract.View {

    @Inject
    lateinit var presenter: FoodDetailContract.Presenter

    private val collapsingTitleTrigger = 0.645

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)
        injectDependencies()
        startAnimations()
        setUpViews()
        val food = intent.extras?.getSerializable(Food::class.java.name) as Food
        bind(food)
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

    private fun injectDependencies() {
        DaggerFoodDetailComponent
                .builder()
                .foodDetailModule(FoodDetailModule(this))
                .build()
                .inject(this)
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
    }

    private fun startAnimations() {
        tvFoodDescription.startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.slide_up))
    }


    fun bind(food: Food) {
        food.user?.let { user ->
            user.profileImage?.let {
                Picasso.get()
                        .load(getDrawableId(it))
                        .transform(CircleTransformation())
                        .into(ivUserPicture)
            }

            tvUserName.text = user.name
        }

        food.image?.let {
            Picasso.get()
                    .load(getDrawableId(it))
                    .into(ivFoodPicture)
        }

        tvFoodDateTime.text = food.dateTime
        tvFoodFavCount.text = food.favoriteCount.toString()
        tvFoodDescription.text = food.description
    }

    private fun getDrawableId(key: String): Int {
        return resources
                .getIdentifier(key.toLowerCase(), "drawable", packageName)
    }
}
