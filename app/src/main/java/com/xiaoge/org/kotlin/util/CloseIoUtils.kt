package com.xiaoge.org.kotlin.util

import java.io.Closeable
import java.io.IOException

object CloseIoUtils {

    /**
     * 关闭IO
     * @param closeables closeables
     */
    fun closeIO(vararg closeables: Closeable) {
        if (closeables == null) return
        closeables
                .filterNotNull()
                .forEach {
                    try {
                        it!!.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
    }

}
