package com.test.foodappchallenge.data.repository

import com.test.foodappchallenge.data.entity.FoodEntity
import com.test.foodappchallenge.data.repository.datasource.FoodDataSource
import com.test.foodappchallenge.data.repository.datasource.LocalStorage
import com.test.foodappchallenge.domain.FoodRepository
import io.reactivex.Observable

class FoodRepositoryImpl(
        private val cloudDataSource: FoodDataSource,
        private val localDataSource: FoodDataSource
) : FoodRepository {

    companion object {
        private var INSTANCE: FoodRepositoryImpl? = null

        @JvmStatic fun getInstance(cloudDataSource: FoodDataSource,
                                   localDataSource: FoodDataSource): FoodRepositoryImpl {
            return INSTANCE ?: FoodRepositoryImpl(cloudDataSource, localDataSource)
                    .apply {
                        INSTANCE = this
                    }
        }

        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getFoods(): Observable<List<FoodEntity>> {
        val local = localDataSource as LocalStorage<List<FoodEntity>>
        val remoteData = cloudDataSource.getFoods()
                .map { local.save(it)
                    it }
                .toObservable()

        if (!local.isCached()) {
            return remoteData
        }

        return Observable.concat(
                localDataSource.getFoods().toObservable(),
                remoteData)
    }

}