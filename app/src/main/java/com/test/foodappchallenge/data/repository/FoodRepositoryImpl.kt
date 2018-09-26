package com.test.foodappchallenge.data.repository

import com.test.foodappchallenge.data.entity.FoodEntity
import com.test.foodappchallenge.data.repository.datasource.FoodDataSource
import com.test.foodappchallenge.data.repository.datasource.LocalStorage
import com.test.foodappchallenge.domain.FoodRepository
import io.reactivex.Completable
import io.reactivex.Observable

class FoodRepositoryImpl(
        private val cloudDataSource: FoodDataSource,
        private val localDataSource: FoodDataSource
) : FoodRepository {

    private var memoryCache: MutableMap<Long, FoodEntity>? = null

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
        if (memoryCache != null) {
            return Observable.just(memoryCache!!.values as List<FoodEntity>)
        }

        val local = localDataSource as LocalStorage<List<FoodEntity>>
        val remoteData = cloudDataSource.getFoods()
                .map {
                    local.save(it)
                    putInMemory(it)
                it}
                .toObservable()

        if (!local.isCached()) {
            return remoteData
        }

        return Observable.concat(
                localDataSource.getFoods().toObservable(),
                remoteData)
    }

    @Suppress("UNCHECKED_CAST")
    override fun updateFood(foodEntity: FoodEntity): Completable {
        return Completable.create { emitter ->
            putInMemory(foodEntity)

            val local = localDataSource as LocalStorage<List<FoodEntity>>
            local.save(memoryCache?.values as List<FoodEntity>)
            emitter.onComplete()
        }
    }

    private fun putInMemory(entities: List<FoodEntity>) {
        if (memoryCache == null) {
            memoryCache = HashMap()
        }

        memoryCache!!.let {
            for (entity in entities) {
                it[entity.id] = entity
            }
        }
    }

    private fun putInMemory(foodEntity: FoodEntity) {
        memoryCache!![foodEntity.id] = foodEntity
    }

}