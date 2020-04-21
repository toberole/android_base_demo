package com.xiaoge.org.service.binderpool;

import android.os.Process;
import android.os.RemoteException;

import com.xiaoge.org.util.LogUtil;

/**
 * Binder_1 通信的服务端实现
 */
public class Binder_1_Impl extends IBinder_1.Stub {
    public static final String TAG = Binder_1_Impl.class.getSimpleName();

    @Override
    public int add(int a, int b) throws RemoteException {
        LogUtil.i(TAG, "Binder_1_Impl#add Thread-Name: " + Thread.currentThread().getName() + " pid: " + Process.myPid());
        return a + b;
    }
}
