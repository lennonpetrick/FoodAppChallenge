package com.test.foodappchallenge.data.entity

data class FoodEntity (
        var id: Long = 0,
        var image: String? = null,
        var description: String? = null,
        var favoriteCount: Int = 0,
        var dateTime: String? = null,
        var favorite: Boolean = false,
        var user: UserEntity? = null
)