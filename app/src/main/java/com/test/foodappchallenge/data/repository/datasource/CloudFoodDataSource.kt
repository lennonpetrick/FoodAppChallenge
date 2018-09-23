package com.test.foodappchallenge.data.repository.datasource

import com.test.foodappchallenge.data.entity.FoodEntity
import io.reactivex.Single

class CloudFoodDataSource : FoodDataSource() {

    private val delay: Long = 1000 * 5
    private val mockedCloudData = ""

    override fun getFoods(): Single<List<FoodEntity>> {
        return Single.create { emitter ->
            Thread.sleep(delay)
            emitter.onSuccess(jsonToEntity(mockedCloudData))
        }
    }

}