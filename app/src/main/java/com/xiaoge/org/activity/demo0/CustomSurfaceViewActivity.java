package com.xiaoge.org.activity.demo0;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.xiaoge.org.R;
import com.xiaoge.org.util.LogUtil;

public class CustomSurfaceViewActivity extends AppCompatActivity {
    public static final String TAG = CustomSurfaceViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_surface_view);
        Log.i(TAG, "pid: " + android.os.Process.myPid());

        LogUtil.i(TAG, "hello log");

        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtil.i(TAG, "hello Thread");
            }
        }).start();
    }
}
