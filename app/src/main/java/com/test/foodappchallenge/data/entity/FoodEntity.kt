package com.test.foodappchallenge.data.entity

import java.util.*

data class FoodEntity (
        var image: Int? = null,
        var description: String? = null,
        var favoriteCount: Int? = null,
        var dateTime: Date? = null,
        var user: UserEntity? = null
)