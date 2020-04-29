package com.xiaoge.org.base_test

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.xiaoge.org.R
import com.xiaoge.org.util.LogUtil
import com.xiaoge.org.view_study.LinearLayout_Scroll

class ActivityLifeTest2 : AppCompatActivity() {
    companion object {
        var TAG = ActivityLifeTest2::class.java.simpleName + "-xxx"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_test2)
        LogUtil.i(TAG, "onCreate")
    }

    private fun test() {
        var intent = Intent()
        intent.resolveActivity(null)
        var sp = getSharedPreferences("", Context.MODE_PRIVATE)
        sp.edit().putBoolean("", true).commit()
        // var msg = Messenger()
//        contentResolver.query()
//        ValueAnimator.ofFloat(1f,10f).addUpdateListener {
//            it.animatedValue
//        }

//        var v: View? = null
//        v.x

        var v: View? = null

        // Thread(null).start()

        var l = LinearLayout(null)
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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }
}
