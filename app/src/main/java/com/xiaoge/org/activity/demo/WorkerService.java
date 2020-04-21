package com.xiaoge.org.activity.demo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.xiaoge.org.util.LogUtil;

import androidx.annotation.Nullable;

public class WorkerService extends Service {
    public static final String TAG = WorkerService.class.getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }
}
