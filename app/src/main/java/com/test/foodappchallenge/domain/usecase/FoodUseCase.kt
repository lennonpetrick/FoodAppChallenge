package com.test.foodappchallenge.domain.usecase

import com.test.foodappchallenge.domain.model.Food
import io.reactivex.Observable

interface FoodUseCase {

    /**
     * It gets a list of food from the repository
     * @return An observable of list of food
     * */
    fun getFoods(): Observable<List<Food>>

}