package com.zw.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;

import java.util.concurrent.ConcurrentHashMap;

public class ZeusServiceConnectionManager {
    public static final String TAG = ZeusServiceConnectionManager.class.getSimpleName();

    private static ConcurrentHashMap<Class<? extends ZeusService>, IZeusService> services = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Class<? extends ZeusService>, ZeusServiceConn> conns = new ConcurrentHashMap<>();

    public void bindService(Context context, String packageName, Class<? extends ZeusService> serviceClass) {
        ZeusServiceConn conn = new ZeusServiceConn(serviceClass);
        conns.putIfAbsent(serviceClass, conn);
        Intent intent;
        if (!TextUtils.isEmpty(packageName)) {
            intent = new Intent(context, serviceClass);
        } else {
            intent = new Intent();
            intent.setClassName(packageName, serviceClass.getName());
        }

        context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    private static class ZeusServiceConn implements ServiceConnection {
        private Class<? extends ZeusService> serviceClass;

        public ZeusServiceConn(Class<? extends ZeusService> serviceClass) {
            this.serviceClass = serviceClass;
        }

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogUtil.i(TAG, "onServiceConnected");
            IZeusService zeusService = IZeusService.Stub.asInterface(iBinder);
            services.putIfAbsent(serviceClass, zeusService);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtil.i(TAG, "onServiceDisconnected componentName: " + String.valueOf(componentName));
        }
    }

    public static ZeusServiceConnectionManager getInstance() {
        return ZeusServiceConnectionManagerHolder.instance;
    }

    private static class ZeusServiceConnectionManagerHolder {
        public static ZeusServiceConnectionManager instance = new ZeusServiceConnectionManager();
    }
}
