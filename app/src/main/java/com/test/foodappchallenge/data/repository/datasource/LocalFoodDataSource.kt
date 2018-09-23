package com.test.foodappchallenge.data.repository.datasource

import android.content.SharedPreferences
import com.test.foodappchallenge.data.entity.FoodEntity
import io.reactivex.Single

class LocalFoodDataSource (
        private val sharedPreferences: SharedPreferences
) : FoodDataSource(), LocalStorage<List<FoodEntity>> {

    override fun getFoods(): Single<List<FoodEntity>> {
        return Single.create { emitter ->
            emitter.onSuccess(jsonToEntity(retrieveData(FoodEntity::class.java.simpleName)!!))
        }
    }

    override fun isCached(): Boolean {
        return sharedPreferences.contains(FoodEntity::class.java.simpleName)
    }

    override fun save(obj: List<FoodEntity>) {
        saveData(FoodEntity::class.java.simpleName, entityToJson(obj))
    }

    private fun retrieveData(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    private fun saveData(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }
}