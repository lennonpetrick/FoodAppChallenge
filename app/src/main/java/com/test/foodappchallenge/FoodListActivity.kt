package com.test.foodappchallenge

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.test.foodappchallenge.domain.model.Food

class FoodListActivity : AppCompatActivity(), FoodListContract.View {

    private lateinit var presenter: FoodListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)
    }

    override fun onResume() {
        super.onResume()
        presenter.load()
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun setFoodList(foods: List<Food>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun displayLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
