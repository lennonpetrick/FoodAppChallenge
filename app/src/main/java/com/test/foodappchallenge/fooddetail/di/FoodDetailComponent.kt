package com.test.foodappchallenge.fooddetail.di

import com.test.foodappchallenge.fooddetail.FoodDetailActivity
import com.test.foodappchallenge.shared.di.ApplicationScope
import dagger.Component

@Component(modules = [FoodDetailModule::class])
@ApplicationScope
interface FoodDetailComponent {
    fun inject(activity: FoodDetailActivity)
}