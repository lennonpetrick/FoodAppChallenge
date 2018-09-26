package com.test.foodappchallenge.domain.model

import java.io.Serializable

data class Food (
        var image: String? = null,
        var description: String? = null,
        var favoriteCount: Int = 0,
        var dateTime: String? = null,
        var favorite: Boolean = false,
        var user: User? = null
) : Serializable