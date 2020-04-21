package com.xiaoge.org.base_test

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xiaoge.org.R
import com.xiaoge.org.util.LogUtil


/*
    activity 生命周期[配置 configChanges]
    屏幕旋转后不会销毁当前activity 回调onConfigurationChanged
        onCreate -> onStart -> onResume -> onPause -> onStop ->
        onSaveInstanceState[直接回到后台才会回到，正常按返回键不会有回调] -> onDestroy
 */
class ActivityLifeTest1 : AppCompatActivity() {
    val TAG = ActivityLifeTest1::class.java.simpleName + "-xxx"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_test)
        var str: String? = null
        savedInstanceState?.let {
            str = it.getString("test-xxx")
        }
        LogUtil.i(TAG, "onCreate str: " + str)
    }

    override fun onStart() {
        super.onStart()
        LogUtil.i(TAG, "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        LogUtil.i(TAG, "onRestart")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.i(TAG, "onResume")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LogUtil.i(TAG, "onSaveInstanceState 1 ...")
        outState.putString("test-xxx", "onSaveInstanceState test ...")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.i(TAG, "onDestroy")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LogUtil.i(TAG, "onConfigurationChanged")
    }
}
