package com.test.foodappchallenge.data.repository

import com.test.foodappchallenge.data.entity.FoodEntity
import com.test.foodappchallenge.data.repository.datasource.CloudFoodDataSource
import com.test.foodappchallenge.data.repository.datasource.LocalFoodDataSource
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class FoodRepositoryTest {

    private lateinit var repository: FoodRepositoryImpl

    @Mock private lateinit var cloudDataSource: CloudFoodDataSource
    @Mock private lateinit var localDataSource: LocalFoodDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val entity = FoodEntity()
        val entities =  arrayListOf<FoodEntity>()
        entities.add(entity)

        `when`(cloudDataSource.getFoods()).thenReturn(Single.just(entities))
        `when`(localDataSource.getFoods()).thenReturn(Single.just(entities))

        repository = FoodRepositoryImpl.getInstance(cloudDataSource, localDataSource)
    }

    @After
    fun tearDown() {
        FoodRepositoryImpl.destroyInstance()
    }

    @Test
    fun `retrieve foods from remote`() {
        `when`(localDataSource.isCached()).thenReturn(false)

        repository.getFoods()
                .test()
                .assertValueCount(1)

        verify(localDataSource, never()).getFoods()
    }

    @Test
    fun `retrieve foods from local and remote`() {
        `when`(localDataSource.isCached()).thenReturn(true)

        repository.getFoods()
                .test()
                .assertValueCount(2)

        verify(localDataSource).getFoods()
    }

    @Test
    fun `retrieve foods from memory`() {
        `when`(localDataSource.isCached()).thenReturn(false)

        repository.getFoods()
                .test()
                .assertValueCount(1)

        repository.getFoods()
                .test()
                .assertValueCount(1)

        verify(localDataSource, never()).getFoods()
        verify(cloudDataSource, times(1)).getFoods()
    }
}