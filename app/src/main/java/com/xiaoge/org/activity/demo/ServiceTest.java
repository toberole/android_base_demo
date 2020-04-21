package com.xiaoge.org.activity.demo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.xiaoge.org.util.LogUtil;

public class ServiceTest extends Service {
    public static final String TAG = ServiceTest.class.getSimpleName();

    public ServiceTest() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG, "ServiceTest#onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        LogUtil.i(TAG, "ServiceTest#onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i(TAG, "ServiceTest#onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG, "ServiceTest#onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.i(TAG, "ServiceTest#onBind");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onRebind(Intent intent) {
        LogUtil.i(TAG, "ServiceTest#onRebind");
        super.onRebind(intent);
    }
}
