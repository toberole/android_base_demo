package com.zw.dagger.bean

import javax.inject.Inject

class Menu @Inject constructor(var menus: MutableMap<String, Boolean>) {
    override fun toString(): String {
        return "Menu(menus=$menus)"
    }
}