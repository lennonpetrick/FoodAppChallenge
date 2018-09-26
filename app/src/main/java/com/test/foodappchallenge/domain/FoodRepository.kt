package com.test.foodappchallenge.domain

import com.test.foodappchallenge.data.entity.FoodEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface FoodRepository {

    /**
     * It gets a list of food from a resource
     *
     * @return An observable of array list of food
     * */
    fun getFoods(): Observable<List<FoodEntity>>

    /**
     * It updates an object in the resource
     *
     * @param foodEntity The object to be updated
     * @return A completable
     * */
    fun updateFood(foodEntity: FoodEntity): Completable
}