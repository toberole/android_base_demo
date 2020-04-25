package com.xiaoge.org.base_test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xiaoge.org.util.LogUtil

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.i(this.javaClass.simpleName + "-xxx", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        LogUtil.i(this.javaClass.simpleName + "-xxx", "onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.i(this.javaClass.simpleName + "-xxx", "onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.i(this.javaClass.simpleName + "-xxx", "onPause")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LogUtil.i(this.javaClass.simpleName + "-xxx", "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        LogUtil.i(this.javaClass.simpleName + "-xxx", "onRestoreInstanceState")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.i(this.javaClass.simpleName + "-xxx", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.i(this.javaClass.simpleName + "-xxx", "onDestroy")
    }
}