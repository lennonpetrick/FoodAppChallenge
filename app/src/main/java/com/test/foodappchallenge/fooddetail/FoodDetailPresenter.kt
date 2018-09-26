package com.test.foodappchallenge.fooddetail

import com.test.foodappchallenge.domain.model.Food
import com.test.foodappchallenge.domain.usecase.FoodUseCase
import io.reactivex.disposables.CompositeDisposable

class FoodDetailPresenter (
        private var view: FoodDetailContract.View?,
        private var useCase: FoodUseCase?
) : FoodDetailContract.Presenter {

    private val disposable = CompositeDisposable()
    private var food: Food? = null

    override fun destroy() {
        view = null
        useCase = null
    }

    override fun loadScreen(food: Food) {
        this.food = food
        view?.let { view ->
            food.user?.let { user ->
                user.profileImage?.let {
                    view.setUserPicture(it)
                }

                view.setUserName(user.name)
            }

            food.image?.let {
                view.setFoodPicture(it)
            }

            view.setDateTime(food.dateTime)
            view.setFavoriteNumber(food.favoriteCount)
            view.setDescription(food.description)
            view.setFavoriteOnOff(food.favorite)
        }
    }

    override fun favoriteFood() {
        view?.let { view ->
            food?.let {
                //it.favoriteCount += 1
                it.favorite = !it.favorite
                //view.setFavoriteNumber(it.favoriteCount)
                view.setFavoriteOnOff(it.favorite)
            }
        }
    }
}