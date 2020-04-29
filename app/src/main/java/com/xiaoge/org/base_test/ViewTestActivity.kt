package com.xiaoge.org.base_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xiaoge.org.R
import kotlinx.android.synthetic.main.activity_view_test.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * postInvalidate,invalidate:
 * 触发onDraw，postInvalidate可以在异步线程中调用
 *
 * requestLayout：触发onMeasure、onLayout、onDraw
 */
class ViewTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_test)
    }

    override fun onResume() {
        super.onResume()
        viewTest.postDelayed(object : Runnable {
            override fun run() {
                viewTest.invalidate()
            }
        }, 2000)

        GlobalScope.launch(Dispatchers.IO) {
            delay(2000)
            viewTest.postInvalidate()
        }

        viewGroupTest.postDelayed(object : Runnable {
            override fun run() {
                viewGroupTest.requestLayout()
            }
        }, 2000)
    }
}
