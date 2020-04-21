package com.xiaoge.org.lifecycle;

import com.xiaoge.org.util.LogUtil;

public class MyLocationListener {
    public static final String TAG = MyLocationListener.class.getSimpleName();

    public void start() {
        LogUtil.i(TAG,"MyLocationListener#start");
    }

    public void stop() {
        LogUtil.i(TAG,"MyLocationListener#stop");
    }
}
