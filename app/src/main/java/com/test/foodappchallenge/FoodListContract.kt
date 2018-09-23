package com.test.foodappchallenge

import com.test.foodappchallenge.domain.model.Food

interface FoodListContract {

    interface View {
        fun setFoodList(foods: List<Food>)
        fun showError(error: String?)
        fun displayLoading()
        fun dismissLoading()
    }

    interface Presenter {
        fun destroy()
        fun load()
    }
}