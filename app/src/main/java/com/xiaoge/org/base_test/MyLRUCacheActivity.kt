package com.xiaoge.org.base_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xiaoge.org.R
import com.xiaoge.org.java_base.MyLRUCache
import com.xiaoge.org.util.LogUtil

class MyLRUCacheActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_l_r_u_cache)
        LogUtil.i("cache-xxx", "MyLRUCacheActivity#onCreate")
        test1()
        // test2()
    }

    private fun test3() {
        var a = "hello"
        var b = "world"

        // == 两个等号意思与Java中的 equals 意思一样
        if (a == b) {
            LogUtil.i("xxx", "a == b")
        }

        // === 三个等号的意思，则比较的是内存地址
        if (a === b) {
            LogUtil.i("xxx", "a === b")
        }
    }

    /**
     * hello2被移除
     */
    private fun test2() {
        var cache: MyLRUCache<String, Test> = MyLRUCache<String, Test>(10)
        cache.put("hello1", Test("hello"))
        cache.put("hello2", Test("world"))
        cache.get("hello1")
        cache.put("hello3", Test("php"))
    }

    /**
     * hello1被移除
     */
    private fun test1() {
        var cache: MyLRUCache<String, Test> = MyLRUCache<String, Test>(10)
        cache.put("hello1", Test("hello"))
        cache.put("hello2", Test("world"))
        cache.put("hello3", Test("android"))
    }

    private inner class Test(var data: String) : Sizer {
        override fun getSize(): Int = data.length

        fun test() {

        }

        override fun toString(): String {
            return data
        }
    }
}
