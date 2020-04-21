package com.zw.dagger

import android.os.Bundle
import android.util.Log
import com.zw.dagger.bean.Chef
import dagger.android.DaggerActivity
import javax.inject.Inject

class TestActivity1 : DaggerActivity() {
    var TAG = TestActivity1::class.java.simpleName + "-xxx"

    @Inject
    lateinit var chef: Chef

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test1)

        Log.i(TAG, "chef: $chef")
    }
}
