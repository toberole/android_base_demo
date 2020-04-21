package com.xiaoge.org.service.binderpool;

import android.os.IBinder;
import android.os.RemoteException;

/**
 * BinderPool 通信的服务端实现
 */
public class BinderPool_Impl extends IBinderPool.Stub {
    @Override
    public IBinder queryBinder(int binderTag) throws RemoteException {
        IBinder binder = null;
        switch (binderTag) {
            case BinderTAG.BINDER_1:
                binder = new Binder_1_Impl();
                break;
            case BinderTAG.BINDER_2:
                binder = new Binder_2_Impl();
                break;
        }
        return binder;
    }
}
