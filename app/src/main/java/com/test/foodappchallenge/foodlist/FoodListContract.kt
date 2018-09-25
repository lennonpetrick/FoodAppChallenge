package com.test.foodappchallenge.foodlist

import com.test.foodappchallenge.domain.model.Food
import com.test.foodappchallenge.shared.BasePresenter

interface FoodListContract {

    interface View {
        fun setFoodList(foods: List<Food>)
        fun showError(error: String?)
        fun displayLoading()
        fun dismissLoading()
    }

    interface Presenter : BasePresenter {
        fun load()
    }
}