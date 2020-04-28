package com.xiaoge.org.base_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xiaoge.org.R
import com.xiaoge.org.util.LogUtil

class ThreadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread)

        TestThread(object : Runnable {
            override fun run() {
                LogUtil.i("xxxxxxx", "TestThread#Runnable#run")
            }
        }).start()

        TestThread_X(object : Runnable {
            override fun run() {
                LogUtil.i("xxxxxxx", "TestThread_X#Runnable#run")
            }
        }).start()

    }

    class TestThread(target: Runnable?) : Thread(target) {
        override fun run() {
            LogUtil.i("xxxxxxx", "TestThread#run")
        }
    }

    class TestThread_X(target: Runnable?) : Thread(target) {
        override fun run() {
            super.run()
            LogUtil.i("xxxxxxx", "TestThread_X#run")
        }
    }
}
