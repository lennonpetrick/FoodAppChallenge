package com.test.foodappchallenge.fooddetail

import com.google.gson.Gson
import com.test.foodappchallenge.BasePresenterTest
import com.test.foodappchallenge.domain.model.Food
import com.test.foodappchallenge.domain.usecase.FoodUseCase
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

class FoodDetailPresenterTest : BasePresenterTest() {

    private lateinit var presenter: FoodDetailContract.Presenter

    @Mock
    private lateinit var view: FoodDetailContract.View
    @Mock
    private lateinit var useCase: FoodUseCase

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
        setUpSchedulers()
        presenter = FoodDetailPresenter(view, useCase)
    }

    @After
    fun tearDown() {
        presenter.destroy()
        resetSchedulers()
    }

    @Test
    fun `load the screen`() {
        val food = Gson().fromJson(json, Food::class.java)

        presenter.loadScreen(food)

        Mockito.verify(view).setUserPicture(anyString())
        Mockito.verify(view).setUserName(anyString())
        Mockito.verify(view).setFoodPicture(anyString())
        Mockito.verify(view).setDateTime(anyString())
        Mockito.verify(view).setFavoriteNumber(anyInt())
        Mockito.verify(view).setDescription(anyString())
        Mockito.verify(view).setFavoriteOnOff(anyBoolean())
    }

    @Test
    fun `favorite food with success`() {
        val food = Gson().fromJson(json, Food::class.java)
        Mockito.`when`(useCase.favoriteFood(food)).thenReturn(Single.just(food))

        presenter.loadScreen(food)
        presenter.favoriteFood()

        Mockito.verify(view, times(2)).setFavoriteNumber(anyInt())
        Mockito.verify(view, times(2)).setFavoriteOnOff(anyBoolean())
        Mockito.verify(view, never()).showError(anyString())
    }

    @Test
    fun `favorite food with failure`() {
        val food = Gson().fromJson(json, Food::class.java)
        Mockito.`when`(useCase.favoriteFood(food)).thenReturn(Single.error(Throwable("")))

        presenter.loadScreen(food)
        presenter.favoriteFood()

        Mockito.verify(view, times(1)).setFavoriteNumber(anyInt())
        Mockito.verify(view, times(1)).setFavoriteOnOff(anyBoolean())
        Mockito.verify(view).showError(anyString())
    }
}