package com.test.foodappchallenge

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers

open class BasePresenterTest {

    protected fun resetSchedulers() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    protected fun setUpSchedulers() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

}