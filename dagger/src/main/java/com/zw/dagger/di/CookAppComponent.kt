package com.zw.dagger.di

import com.zw.dagger.App
import dagger.Component
import dagger.android.AndroidInjector

import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            ActivityModules::class,
            CookModules::class
        ]
)

interface CookAppComponent : AndroidInjector<App?> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App?>()
}
