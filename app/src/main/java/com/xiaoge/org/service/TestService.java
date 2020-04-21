package com.xiaoge.org.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.xiaoge.org.bean.IOnNewUserAdded;
import com.xiaoge.org.bean.IUserManager;
import com.xiaoge.org.bean.User;

import java.util.List;

public class TestService extends Service {
    public static final String TAG = "TestServiceActivity";

    private IUserManager.Stub iUserManager = new IUserManager.Stub() {
        @Override
        public void addUser(User user) throws RemoteException {
            Log.i(TAG, "TestService#addUser");
        }

        @Override
        public List<User> getUsers() throws RemoteException {
            Log.i(TAG, "TestService#getUsers");
            return null;
        }

        @Override
        public void registerListener(IOnNewUserAdded listener) throws RemoteException {
            Log.i(TAG, "TestService#registerListener");
        }

        @Override
        public void unRegisterListener(IOnNewUserAdded listener) throws RemoteException {
            Log.i(TAG, "TestService#unRegisterListener");
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "TestService#onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "TestService#onStartCommand intent: " + intent + " flags: " + flags + " startId: " + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iUserManager;
    }
}
