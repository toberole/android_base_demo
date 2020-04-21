package com.xiaoge.org.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CalService extends Service {
    public static final String TAG = CalService.class.getSimpleName();

    public CalService() {
        Log.i(TAG,"CalService#CalService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"CalService#onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(TAG,"CalService#onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"CalService#onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"CalService#onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
