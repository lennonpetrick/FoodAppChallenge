package com.test.foodappchallenge.data.repository.datasource

import android.content.Context
import com.test.foodappchallenge.data.entity.FoodEntity
import io.reactivex.Single

class CloudFoodDataSource(private val context: Context) : FoodDataSource() {

    private val delay: Long = 1000 * 5

    override fun getFoods(): Single<List<FoodEntity>> {
        return Single.create { emitter ->
            Thread.sleep(delay)
            emitter.onSuccess(jsonToEntity(getJsonData("data.json")))
        }
    }

    private fun getJsonData(file: String): String {
        val inputStream = context.assets.open(file)
        val buffer = ByteArray(inputStream.available())

        inputStream.read(buffer)
        inputStream.close()

        return String(buffer)
    }

}