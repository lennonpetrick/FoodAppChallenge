package com.test.foodappchallenge.fooddetail

import com.test.foodappchallenge.domain.model.Food
import com.test.foodappchallenge.shared.BasePresenter

interface FoodDetailContract {
    interface View {
        fun setUserPicture(image: String)
        fun setUserName(name: String?)
        fun setDateTime(dateTime: String?)
        fun setDescription(description: String?)
        fun setFavoriteNumber(number: Int?)
        fun setFoodPicture(image: String)
        fun setFavoriteOnOff(param: Boolean)
        fun showError(error: String?)
    }

    interface Presenter : BasePresenter {
        fun loadScreen(food: Food)
        fun favoriteFood()
    }
}