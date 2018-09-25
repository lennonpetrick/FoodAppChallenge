package com.test.foodappchallenge.foodlist.di

import com.test.foodappchallenge.foodlist.FoodListActivity
import com.test.foodappchallenge.shared.di.ApplicationScope
import dagger.Component

@Component(modules = [FoodListModule::class])
@ApplicationScope
interface FoodListComponent {
    fun inject(activity: FoodListActivity)
}