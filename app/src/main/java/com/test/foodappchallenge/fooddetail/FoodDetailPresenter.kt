package com.test.foodappchallenge.fooddetail

import com.test.foodappchallenge.domain.usecase.FoodUseCase
import io.reactivex.disposables.CompositeDisposable

class FoodDetailPresenter (
        private var view: FoodDetailContract.View?,
        private var useCase: FoodUseCase?
) : FoodDetailContract.Presenter {

    private val disposable = CompositeDisposable()

    override fun destroy() {
        view = null
        useCase = null
    }


}