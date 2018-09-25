package com.test.foodappchallenge.domain.model

import java.io.Serializable

data class Food (
        var image: String? = null,
        var description: String? = null,
        var favoriteCount: Int? = null,
        var dateTime: String? = null,
        var user: User? = null
) : Serializable