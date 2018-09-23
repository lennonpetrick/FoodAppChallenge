package com.test.foodappchallenge.di

import android.content.Context
import android.content.SharedPreferences
import com.test.foodappchallenge.FoodListActivity
import com.test.foodappchallenge.FoodListContract
import com.test.foodappchallenge.FoodListPresenter
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
class FoodListModule(private val context: Context) {

    @Provides
    fun view(): FoodListContract.View {
        return context as FoodListContract.View
    }

    @Provides
    @Inject
    fun presenter(view: FoodListContract.View, useCase: FoodUseCase): FoodListContract.Presenter {
        return FoodListPresenter(view, useCase)
    }

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
        return CloudFoodDataSource()
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
                FoodListActivity::class.java.name,
                Context.MODE_PRIVATE)
    }

}