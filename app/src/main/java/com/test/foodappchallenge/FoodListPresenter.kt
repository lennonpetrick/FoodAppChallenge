package com.test.foodappchallenge

import com.test.foodappchallenge.domain.usecase.FoodUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FoodListPresenter (
        private var view: FoodListContract.View?,
        private var useCase: FoodUseCase?
) : FoodListContract.Presenter {

    private val disposable = CompositeDisposable()

    override fun destroy() {
        view = null
        useCase = null
    }

    override fun load() {
        view?.displayLoading()
        disposable.add(useCase!!.getFoods()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    view?.dismissLoading()
                }
                .subscribe( {
                    foods -> view?.setFoodList(foods)
                }, {
                    throwable -> view?.showError(throwable.message)
                }))
    }

}