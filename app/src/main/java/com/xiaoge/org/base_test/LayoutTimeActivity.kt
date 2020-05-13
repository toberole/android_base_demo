package com.xiaoge.org.base_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xiaoge.org.R

/**
 * 测量布局耗时 LayoutInflaterCompat.setFactory2
 */
class LayoutTimeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_time)


    }
}
