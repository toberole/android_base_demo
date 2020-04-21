package com.zw.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class ZeusService extends Service {
    public static final String TAG = ZeusService.class.getSimpleName();

    IZeusService.Stub zeusService = new IZeusService.Stub() {
        @Override
        public Response postRequest(Request request) throws RemoteException {
            LogUtil.i(TAG, "postRequest: " + request);
            switch (request.requestType) {
                case ZeusIPC.TYPE_NEW:
                    break;
                case ZeusIPC.TYPE_GET:
                    break;
                default:
                    break;
            }
            return null;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.i(TAG, "onBind");
        return null;
    }
}
