package com.test.foodappchallenge.data.repository.datasource

interface LocalStorage<T> {
    fun isCached(): Boolean
    fun save(obj: T)
}