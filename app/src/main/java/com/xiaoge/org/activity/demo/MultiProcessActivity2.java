package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.xiaoge.org.App;
import com.xiaoge.org.R;
import com.xiaoge.org.util.LogUtil;

public class MultiProcessActivity2 extends AppCompatActivity {
    public static final String TAG = MultiProcessActivity2.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_process2);
        LogUtil.i(TAG, "MultiProcessActivity2: " + App.count);
    }
}
