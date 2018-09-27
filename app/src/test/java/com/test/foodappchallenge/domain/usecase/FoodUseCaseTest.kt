package com.test.foodappchallenge.domain.usecase

import com.google.gson.Gson
import com.test.foodappchallenge.domain.FoodRepository
import com.test.foodappchallenge.domain.model.Food
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

private fun <T> anyObject(): T {
    return Mockito.any<T>()
}

class FoodUseCaseTest {

    private lateinit var usecase: FoodUseCase

    @Mock
    private lateinit var repository: FoodRepository

    private val json = "{\n" +
            "    \"id\": 1,\n" +
            "    \"image\": \"food1\",\n" +
            "    \"description\": \"Lorem ipsum dolor sit amet\",\n" +
            "    \"favoriteCount\": 1346,\n" +
            "    \"dateTime\": \"Today, 1:45PM\",\n" +
            "    \"favorite\": false,\n" +
            "    \"user\": {\n" +
            "      \"name\": \"Jess Mckenzie\",\n" +
            "      \"profileImage\": \"profile1\"\n" +
            "    }\n" +
            "  }"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        usecase = FoodUseCaseImpl(repository)
    }

    @Test
    fun `favorite on`() {
        `when`(repository.updateFood(anyObject())).thenReturn(Completable.complete())

        val food = Gson().fromJson(json, Food::class.java)
        food.favorite = false
        food.favoriteCount = 1

        usecase.favoriteFood(food)
                .test()
                .assertValue { food.favorite && food.favoriteCount == 2 }
    }

    @Test
    fun `favorite off`() {
        `when`(repository.updateFood(anyObject())).thenReturn(Completable.complete())

        val food = Gson().fromJson(json, Food::class.java)
        food.favorite = true
        food.favoriteCount = 2

        usecase.favoriteFood(food)
                .test()
                .assertValue { !food.favorite && food.favoriteCount == 1 }
    }
}