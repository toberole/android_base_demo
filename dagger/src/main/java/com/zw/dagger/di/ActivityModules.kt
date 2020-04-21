package com.zw.dagger.di

import com.zw.dagger.TestActivity1
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityModules {
    /*
        @ContributesAndroidInjector
        来标记哪个类需要使用依赖注入功能
     */
    @ContributesAndroidInjector
    abstract fun contributeTestActivity1(): TestActivity1
}
