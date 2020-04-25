package com.xiaoge.org.base_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xiaoge.org.R
import com.xiaoge.org.util.LogUtil

class ActivityLifeTest2 : AppCompatActivity() {
    companion object {
        var TAG = ActivityLifeTest2::class.java.simpleName + "-xxx"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_test2)
        LogUtil.i(TAG, "onCreate")
    }

    override fun onStart() {
        super.onStart()
        LogUtil.i(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.i(TAG, "onPause")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LogUtil.i(TAG, "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        LogUtil.i(TAG, "onRestoreInstanceState")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.i(TAG, "onDestroy")
    }
}
