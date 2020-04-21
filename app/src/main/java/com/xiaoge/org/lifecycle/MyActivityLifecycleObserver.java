package com.xiaoge.org.lifecycle;

import com.xiaoge.org.util.LogUtil;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MyActivityLifecycleObserver implements LifecycleObserver {
    public static final String TAG = MyActivityLifecycleObserver.class.getSimpleName();

    private LifecycleActivity lifecycleActivity;
    private Lifecycle lifecycle;
    private MyLocationListener myLocationListener;

    public MyActivityLifecycleObserver(LifecycleActivity lifecycleActivity, Lifecycle lifecycle, MyLocationListener myLocationListener) {
        this.lifecycleActivity = lifecycleActivity;
        this.lifecycle = lifecycle;
        this.myLocationListener = myLocationListener;
        this.lifecycle.addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void test_resume() {
        LogUtil.i(TAG, "test_resume");
        myLocationListener.start();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void test_stop() {
        LogUtil.i(TAG, "test_stop");

        // 查询当前状态
        if (lifecycle.getCurrentState().isAtLeast(Lifecycle.State.DESTROYED)) {
            LogUtil.i(TAG, "test_stop CurrentState DESTROYED");
        }


        myLocationListener.stop();
    }
}
