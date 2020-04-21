package com.zw.dagger

import com.zw.dagger.di.DaggerCookAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out App?>? {
        return DaggerCookAppComponent.builder().create(this)
    }
}