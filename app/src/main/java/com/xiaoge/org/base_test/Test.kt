package com.xiaoge.org.base_test

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.ArrayMap
import android.util.SparseArray
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class Test {
    fun test() {
        var msg = Message.obtain()
        Looper.prepare()
        Looper.loop()
        var handler = Handler()

        var sa = SparseArray<Int>()
        sa.put(0, 0)
        var arrayMap = ArrayMap<String, String>()
        arrayMap.put("", "")

        var ctx: Context? = null
        ctx?.getSharedPreferences("", Context.MODE_PRIVATE)

        var threadPoolExecutor = ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, LinkedBlockingDeque())
        threadPoolExecutor.execute({

        })
    }
}