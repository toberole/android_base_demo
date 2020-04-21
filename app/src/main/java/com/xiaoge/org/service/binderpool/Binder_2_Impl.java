package com.xiaoge.org.service.binderpool;

import android.os.RemoteException;
import android.os.Process;
import com.xiaoge.org.util.LogUtil;

/**
 * Binder_2 通信的服务端实现
 */
public class Binder_2_Impl extends IBinder_2.Stub {
    public static final String TAG = Binder_2_Impl.class.getCanonicalName();

    @Override
    public void printHello(String msg) throws RemoteException {
        LogUtil.i(TAG, "Binder_2_Impl#printHello Thread-Name: " + Thread.currentThread().getName() + " pid: " + Process.myPid() + " " + msg);
    }
}
