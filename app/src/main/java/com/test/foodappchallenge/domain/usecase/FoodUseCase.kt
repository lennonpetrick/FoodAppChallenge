package com.test.foodappchallenge.domain.usecase

import com.test.foodappchallenge.domain.model.Food
import io.reactivex.Observable
import io.reactivex.Single

interface FoodUseCase {

    /**
     * It gets a list of food from the repository
     *
     * @return An observable of list of food
     * */
    fun getFoods(): Observable<List<Food>>

    /**
     * It marks a object as a favorite one or remove as favorite one
     * depending on the current favorite status of the object
     *
     * @param food The object to mark as favorite or not
     * @return A single of the updated object
     * */
    fun favoriteFood(food: Food): Single<Food>
}