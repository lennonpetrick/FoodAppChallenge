package com.test.foodappchallenge.shared.di

import android.content.Context
import android.content.SharedPreferences
import com.test.foodappchallenge.BuildConfig
import com.test.foodappchallenge.data.repository.FoodRepositoryImpl
import com.test.foodappchallenge.data.repository.datasource.CloudFoodDataSource
import com.test.foodappchallenge.data.repository.datasource.FoodDataSource
import com.test.foodappchallenge.data.repository.datasource.LocalFoodDataSource
import com.test.foodappchallenge.domain.FoodRepository
import com.test.foodappchallenge.domain.usecase.FoodUseCase
import com.test.foodappchallenge.domain.usecase.FoodUseCaseImpl
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Named

@Module
open class BaseModule (protected val context: Context) {

    @Provides
    @Inject
    fun useCase(repository: FoodRepository): FoodUseCase {
        return FoodUseCaseImpl(repository)
    }

    @Provides
    @Inject
    fun repository(@Named("cloud") cloudDataSource: FoodDataSource,
                   @Named("local") localDataSource: FoodDataSource): FoodRepository {
        return FoodRepositoryImpl(cloudDataSource, localDataSource)
    }

    @Provides
    @Named("cloud")
    fun cloudDataSource(): FoodDataSource {
        return CloudFoodDataSource(context)
    }

    @Provides
    @Inject
    @Named("local")
    fun localDataSource(sharedPreferences: SharedPreferences): FoodDataSource {
        return LocalFoodDataSource(sharedPreferences)
    }

    @Provides
    fun sharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(
                BuildConfig.APPLICATION_ID,
                Context.MODE_PRIVATE)
    }

}