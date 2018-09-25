package com.test.foodappchallenge.fooddetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)
        injectDependencies()
        setUpViews()
        val food = intent.extras?.getSerializable(Food::class.java.name) as Food
        bind(food)
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
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
