package com.zw.dagger.bean

import javax.inject.Inject

class Chef @Inject constructor(var menu: Menu) : Cooking {
    override fun cook(): String {
        var menus = menu.menus
        var string = StringBuffer()

        for (en in menus.entries) {
            string.append("${en.key},${en.value}\n")
        }

        return string.toString()
    }

    override fun toString(): String {
        return "Chef(menu=$menu)"
    }


}