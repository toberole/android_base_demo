package com.xiaoge.org.activity.kotlin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.xiaoge.org.Constant
import com.xiaoge.org.R
import com.xiaoge.org.R2.id.async
import com.xiaoge.org.kotlin.demo.log_i
import kotlinx.android.synthetic.main.hello_kotlin.*
import kotlinx.coroutines.*

/**
 * 引用this 类似java的Activity.this
 * this@HelloKotlinActivity
 */
class HelloKotlinActivity : AppCompatActivity(), View.OnClickListener {

    // 通过创建一个 CoroutineScope 实例来管理协程的生命周期，
    // 并使它与 activit 的生命周期相关联。CoroutineScope
    // 可以通过 CoroutineScope() 创建或者通过MainScope() 工厂函数。
    // 前者创建了一个通用作用域，而后者为使用 Dispatchers.
    // Main 作为默认调度器的 UI 应用程序 创建作用域
    private val mainScop = MainScope()


    // 需要引用kotlin反射包
    // private var TAG = HelloKotlinActivity::class.simpleName
    companion object {
        // public static final String TAG = HelloKotlinActivity.class.getSimpleName();
        private val TAG = HelloKotlinActivity::class.java.simpleName
    }

    private lateinit var et_name: EditText
    private lateinit var btn_login: Button

    // object
    private var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hello_kotlin)
        initView()

        // 包级函数引用
        log_i(Constant.DREAM_LOG)
        LogUtils.d("hh", "hello")

        // 协程 默认是运行在当前线程
        GlobalScope.launch {
            delay(3000)

            // 指定协程在主线程中运行
            launch(Dispatchers.Main) { tv_title.text = "更新UI" }
//            runOnUiThread {
//
//            }
        }

        //
        GlobalScope.launch(newSingleThreadContext("default")) {
            launch(Dispatchers.Main) { tv_title.text = "***更新UI***" }
        }
    }

    private fun initView() {
        et_name = findViewById(R.id.et_name)

        btn_login = findViewById(R.id.btn_login)
        btn_login.setOnClickListener(this)

        // 不用findViewById tv_title
        btn_reg.setOnClickListener(this)

        btn_test1.setOnClickListener(this)

        // 不用findViewById tv_title
        tv_title.text = "Hello Kotlin"
    }

    override fun onClick(v: View?) {
        var id = v?.id
        when (id) {
            R.id.btn_login -> {
                var name: String?
                name = et_name.text.toString().trim()
                Log.i(TAG, "login name: " + name)

                if (name.isNotEmpty()) {
                    Toast.makeText(this@HelloKotlinActivity, name, Toast.LENGTH_SHORT).show()
                }
            }

            R.id.btn_reg -> {
                Log.i(TAG, "reg")

                // ::双冒号是 JDK1.8 的新特性，表示方法引用
                var intent = Intent(this@HelloKotlinActivity, DemoKotlinActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_test1 -> {
                // 调用kotlin包级别函数
                log_i("hahahaha")
            }

            R.id.btn_c -> {
            }
        }
    }
}