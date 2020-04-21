package com.xiaoge.org.base_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import butterknife.ButterKnife
import com.xiaoge.org.R
import com.xiaoge.org.util.LogUtil
import kotlinx.android.synthetic.main.activity_dispatch_touch_event.*

class DispatchTouchEventActivity : AppCompatActivity() {
    var TAG = "DispatchTouchEvent-xxx"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dispatch_touch_event)

        btn_test.setOnClickListener({
            LogUtil.i(TAG, "DispatchTouchEventActivity#btn_test clicked")
        })

        btn_test.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                LogUtil.i(TAG, "DispatchTouchEventActivity#btn_test onTouch")
                return false
            }
        })

        var btn = Button(this)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        LogUtil.i(TAG, "DispatchTouchEventActivity#dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }
}
