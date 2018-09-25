package com.test.foodappchallenge.fooddetail.di

import android.content.Context
import com.test.foodappchallenge.domain.usecase.FoodUseCase
import com.test.foodappchallenge.fooddetail.FoodDetailContract
import com.test.foodappchallenge.fooddetail.FoodDetailPresenter
import com.test.foodappchallenge.shared.di.BaseModule
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class FoodDetailModule(context: Context) : BaseModule(context) {

    @Provides
    fun view(): FoodDetailContract.View {
        return context as FoodDetailContract.View
    }

    @Provides
    @Inject
    fun presenter(view: FoodDetailContract.View, useCase: FoodUseCase): FoodDetailContract.Presenter {
        return FoodDetailPresenter(view, useCase)
    }

}