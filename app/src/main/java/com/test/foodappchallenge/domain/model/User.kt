package com.test.foodappchallenge.domain.model

import java.io.Serializable

data class User (
    var name: String? = null,
    var profileImage: Int? = null
) : Serializable