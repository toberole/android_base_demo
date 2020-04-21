package com.xiaoge.org.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
        Log.i("test_xxxx", "MyService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("test_xxxx", "MyService#onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int ret = super.onStartCommand(intent, flags, startId);
        Log.i("test_xxxx", "MyService#onStartCommand");
        return ret;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("test_xxxx", "MyService#onDestroy");
    }

    @Override
    /**
     * return null 对方的ServiceConnection不会回调
     */
    public IBinder onBind(Intent intent) {
        Log.i("test_xxxx", "MyService#onBind");
        return new LocalBinder();
        // return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("test_xxxx", "MyService#onUnbind");
        return super.onUnbind(intent);
    }

    public static class LocalBinder extends Binder {
        public void hello() {
            Log.i("test_xxxx", "LocalBinder#hello");
        }
    }
}
