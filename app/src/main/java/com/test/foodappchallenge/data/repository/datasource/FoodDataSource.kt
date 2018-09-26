package com.test.foodappchallenge.data.repository.datasource

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.test.foodappchallenge.data.entity.FoodEntity
import io.reactivex.Single

abstract class FoodDataSource {

    private var gson: Gson? = null
    get() {
        if (field == null) {
            field = Gson()
        }

        return field
    }

    protected fun jsonToEntity(json: String): List<FoodEntity> {
        return gson!!.fromJson(json, object: TypeToken<List<FoodEntity>>(){}.type)
    }

    protected fun entityToJson(entities: List<FoodEntity>): String {
        return gson!!.toJson(entities)
    }

    /**
     * It gets a list of food from a resource
     *
     * @return A single of array list of food
     * */
    abstract fun getFoods(): Single<List<FoodEntity>>
}