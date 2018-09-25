package com.test.foodappchallenge.foodlist.di

import android.content.Context
import com.test.foodappchallenge.domain.usecase.FoodUseCase
import com.test.foodappchallenge.foodlist.FoodListContract
import com.test.foodappchallenge.foodlist.FoodListPresenter
import com.test.foodappchallenge.shared.di.BaseModule
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class FoodListModule(context: Context) : BaseModule(context) {

    @Provides
    fun view(): FoodListContract.View {
        return context as FoodListContract.View
    }

    @Provides
    @Inject
    fun presenter(view: FoodListContract.View, useCase: FoodUseCase): FoodListContract.Presenter {
        return FoodListPresenter(view, useCase)
    }

}