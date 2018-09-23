package com.test.foodappchallenge.domain.usecase

import com.test.foodappchallenge.domain.FoodRepository
import com.test.foodappchallenge.domain.model.Food
import com.test.foodappchallenge.domain.model.mapper.foodEntityListToModelList
import io.reactivex.Observable

class FoodUseCaseImpl(private val repository: FoodRepository) : FoodUseCase {

    override fun getFoods(): Observable<List<Food>> {
        return repository.getFoods()
                .map { foodEntityListToModelList(it) }
    }
}