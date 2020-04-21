package com.xiaoge.org.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;

public class IntentService_Test extends IntentService {
    public static final String TAG = IntentService_Test.class.getSimpleName();
    private static int n = 0;

    public IntentService_Test() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "IntentService_Test onCreate " + n++);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.i(TAG, "IntentService_Test onStartCommand startId: " + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "onHandleIntent Thread-name: " + Looper.myLooper());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "IntentService_Test onDestroy");
    }
}
