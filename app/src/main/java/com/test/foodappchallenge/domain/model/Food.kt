package com.test.foodappchallenge.domain.model

import java.io.Serializable
import java.util.*

data class Food (
        var image: Int? = null,
        var description: String? = null,
        var favoriteCount: Int? = null,
        var dateTime: Date? = null,
        var user: User? = null
) : Serializable