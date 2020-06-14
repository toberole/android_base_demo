package com.xiaoge.org.jni

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.xiaoge.org.R
import kotlinx.android.synthetic.main.activity_j_n_i__example_.*

class JNI_Example_Activity : AppCompatActivity(), View.OnClickListener {
    private lateinit var jni_example: Example
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_j_n_i__example_)
        jni_example = Example()
        btn_smart_point.setOnClickListener(this);
        btn_mutli_thread.setOnClickListener(this);
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_smart_point -> {
                jni_example.smart_point()
            }
            R.id.btn_mutli_thread -> {
                jni_example.mutli_thread()
            }
        }
    }
}
