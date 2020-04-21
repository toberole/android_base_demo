package com.xiaoge.org.activity.kotlin

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.xiaoge.org.R
import kotlinx.android.synthetic.main.activity_demo_kotlin1.*
import kotlinx.coroutines.*

class DemoKotlinActivity_1 : AppCompatActivity(), View.OnClickListener {
    // UI 线程的协程作用域
    private val mainScop = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_kotlin1)

        initView()
    }

    private fun initView() {
        btn_update.setOnClickListener(this@DemoKotlinActivity_1)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_update -> {
                mainScop.launch {
                    delay(200)

                    tv_xxxx.text = System.currentTimeMillis().toString()
                }
                tv_xxxx.text = System.currentTimeMillis().toString()

                // Dispatchers.Default 非UI线程中执行
                GlobalScope.launch(Dispatchers.Default) {
                    Log.i("xxxxx", "thread name: " + Thread.currentThread().name)
                }
            }
        }
    }
}