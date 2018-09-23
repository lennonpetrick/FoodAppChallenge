package com.test.foodappchallenge.di

import com.test.foodappchallenge.FoodListActivity
import dagger.Component

@Component(modules = [FoodListModule::class])
@ApplicationScope
interface FoodListComponent {
    fun inject(activity: FoodListActivity)
}