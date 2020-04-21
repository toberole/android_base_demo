package com.xiaoge.org.service.binderpool;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.xiaoge.org.util.LogUtil;

public class BinderPoolService extends Service {
    public static final String TAG = BinderPoolService.class.getSimpleName();

    private Binder binderPool = new BinderPool_Impl();

    public BinderPoolService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG, "BinderPoolService#onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.i(TAG, "BinderPoolService#onBind");
        return binderPool;
    }

    @Override
    public void onDestroy() {
        LogUtil.i(TAG, "BinderPoolService#onDestroy");
        super.onDestroy();
    }
}
