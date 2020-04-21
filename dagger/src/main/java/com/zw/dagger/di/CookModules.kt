package com.zw.dagger.di

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CookModules {
    @Singleton
    @Provides
    fun providerMenus(): Map<String, Boolean> {
        val menus: MutableMap<String, Boolean> = LinkedHashMap()
        menus["酸菜鱼"] = true
        menus["土豆丝"] = true
        menus["铁板牛肉"] = true
        return menus
    }
}
