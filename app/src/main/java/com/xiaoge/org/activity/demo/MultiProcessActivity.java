package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.xiaoge.org.App;
import com.xiaoge.org.R;
import com.xiaoge.org.util.LogUtil;

public class MultiProcessActivity extends AppCompatActivity {
    public static final String TAG = MultiProcessActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_process);

        LogUtil.i(TAG, "MultiProcessActivity: " + App.count);
    }
}
