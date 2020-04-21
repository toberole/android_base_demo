package com.xiaoge.org.lifecycle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import android.os.Bundle;

import com.xiaoge.org.R;
import com.xiaoge.org.util.LogUtil;

public class LifecycleActivity extends AppCompatActivity {
    public static final String TAG = LifecycleActivity.class.getSimpleName();

    private MyLocationListener myLocationListener = new MyLocationListener();

    private MyActivityLifecycleObserver myActivityLifecycleObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);
        myActivityLifecycleObserver = new MyActivityLifecycleObserver(this, getLifecycle(), myLocationListener);
    }
}