package com.test.foodappchallenge.foodlist

import com.test.foodappchallenge.BasePresenterTest
import com.test.foodappchallenge.domain.model.Food
import com.test.foodappchallenge.domain.usecase.FoodUseCase
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class FoodListPresenterTest : BasePresenterTest() {

    private lateinit var presenter: FoodListContract.Presenter

    @Mock private lateinit var view: FoodListContract.View
    @Mock private lateinit var useCase: FoodUseCase
    @Mock private lateinit var foodList: List<Food>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        setUpSchedulers()
        presenter = FoodListPresenter(view, useCase)
    }

    @After
    fun tearDown() {
        presenter.destroy()
        resetSchedulers()
    }

    @Test
    fun `load with success`() {
        `when`(useCase.getFoods()).thenReturn(Observable.just(foodList))

        presenter.load()

        verify(view).displayLoading()
        verify(view).dismissLoading()
        verify(view).setFoodList(anyList())
        verify(view, never()).showError(anyString())
    }

    @Test
    fun `load with failure`() {
        `when`(useCase.getFoods()).thenReturn(Observable.error(Throwable("")))

        presenter.load()

        verify(view).displayLoading()
        verify(view).dismissLoading()
        verify(view).showError(anyString())
        verify(view, never()).setFoodList(anyList())
    }
}