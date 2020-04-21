package com.xiaoge.org.util;

import android.text.TextUtils;
import android.util.Log;

public class LogUtil {
    private static final String DEFAULT_TAG = "sogou";

    private static boolean enableLog = true;

    public static void enableLog(boolean enableLog) {
        LogUtil.enableLog = enableLog;
    }

    public static void v(String tag, String msg) {
        if (enableLog && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.v(tag, msg);
        }
    }

    public static void v(String msg) {
        if (enableLog && !TextUtils.isEmpty(msg)) {
            Log.v(DEFAULT_TAG, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (enableLog && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.d(tag, msg);
        }
    }

    public static void d(String msg) {
        if (enableLog && !TextUtils.isEmpty(msg)) {
            Log.d(DEFAULT_TAG, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (enableLog && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.i(tag, msg);
        }
    }

    public static void i(String msg) {
        if (enableLog && !TextUtils.isEmpty(msg)) {
            Log.i(DEFAULT_TAG, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (enableLog && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.w(tag, msg);
        }
    }

    public static void w(String msg) {
        if (enableLog && !TextUtils.isEmpty(msg)) {
            Log.w(DEFAULT_TAG, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (enableLog && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.e(tag, msg);
        }
    }

    public static void e(String msg) {
        if (enableLog && !TextUtils.isEmpty(msg)) {
            Log.e(DEFAULT_TAG, msg);
        }
    }
}
