package com.test.foodappchallenge.domain.model.mapper

import com.test.foodappchallenge.data.entity.FoodEntity
import com.test.foodappchallenge.data.entity.UserEntity
import com.test.foodappchallenge.domain.model.Food
import com.test.foodappchallenge.domain.model.User

internal fun foodEntityListToModelList(entities: List<FoodEntity>): List<Food> {
    val models = arrayListOf<Food>()
    for (entity in entities) {
        models.add(foodEntityToModel(entity))
    }

    return models
}

internal fun foodEntityToModel(entity: FoodEntity): Food {
    return Food(
            user = userEntityToModel(entity.user!!),
            image = entity.image,
            description = entity.description,
            favoriteCount = entity.favoriteCount,
            dateTime = entity.dateTime
    )
}

internal fun userEntityToModel(entity: UserEntity): User {
    return User(
            entity.name,
            entity.profileImage
    )
}