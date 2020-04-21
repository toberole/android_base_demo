package com.xiaoge.org.service.binderpool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;

public class Business_BinderPool {
    public static final String TAG = Business_BinderPool.class.getSimpleName();

    private Context context;

    private IBinderPool binderPool_impl;

    public void init(@NonNull Context ctx) {
        this.context = ctx.getApplicationContext();
    }

    public void binderServiceBinderPool() {
        Log.e(TAG, "binderServiceBinderPool " + System.currentTimeMillis());

        Intent intent = new Intent(context, BinderPoolService.class);
        context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    public IBinder queryBinder(int binderTag) {
        try {
            return binderPool_impl.queryBinder(binderTag);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected");
            binderPool_impl = IBinderPool.Stub.asInterface(service);
            try {
                // 服务断链通知
                binderPool_impl.asBinder().linkToDeath(recipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected");
        }
    };


    private IBinder.DeathRecipient recipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e(TAG, "binderDied");
            binderPool_impl.asBinder().unlinkToDeath(recipient, 0);

            // 重新链接服务
            binderPool_impl = null;
            binderServiceBinderPool();
        }
    };

    public static Business_BinderPool getInstance() {
        return Business_BinderPoolHolder.instance;
    }

    private Business_BinderPool() {

    }

    private static class Business_BinderPoolHolder {
        public static Business_BinderPool instance = new Business_BinderPool();
    }
}
