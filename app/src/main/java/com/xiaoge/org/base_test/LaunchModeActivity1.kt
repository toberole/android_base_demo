package com.xiaoge.org.base_test

import android.content.Intent
import android.os.Bundle
import com.xiaoge.org.R
import kotlinx.android.synthetic.main.activity_launch_mode1.*

class LaunchModeActivity1 : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_mode1)

        tv_test.setOnClickListener({
            var intent = Intent(this@LaunchModeActivity1, LaunchModeActivity2::class.java)
            startActivity(intent)
        })
    }
}
