package com.test.foodappchallenge.domain.usecase

import com.test.foodappchallenge.domain.FoodRepository
import com.test.foodappchallenge.domain.model.Food
import com.test.foodappchallenge.domain.model.mapper.foodEntityListToModelList
import com.test.foodappchallenge.domain.model.mapper.modelToFoodEntity
import io.reactivex.Observable
import io.reactivex.Single

class FoodUseCaseImpl(private val repository: FoodRepository) : FoodUseCase {

    override fun getFoods(): Observable<List<Food>> {
        return repository.getFoods()
                .map { foodEntityListToModelList(it) }
    }

    override fun favoriteFood(food: Food): Single<Food> {
        food.favorite = !food.favorite
        if (food.favorite) {
            food.favoriteCount += 1
        } else {
            food.favoriteCount -= 1
        }

        return repository.updateFood(modelToFoodEntity(food))
                .toSingle { food }
    }
}