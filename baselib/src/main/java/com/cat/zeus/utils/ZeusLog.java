package com.cat.zeus.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ZeusLog {
    public static final String TAG = "com-org-cn";

    public static final int ORIGIN_STACK_INDEX = 4;

    private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private static boolean enableLog = true;

    public static void enableLog(boolean enableLog) {
        ZeusLog.enableLog = enableLog;
    }

    public static void v(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            v(TAG, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.v(tag, formatMsg(tag, msg));
        }
    }

    public static void d(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            d(TAG, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.d(tag, formatMsg(tag, msg));
        }
    }

    public static void i(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            i(TAG, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.i(tag, formatMsg(tag, msg));
        }
    }

    public static void w(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            w(TAG, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.w(tag, formatMsg(tag, msg));
        }
    }

    public static void e(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            e(TAG, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.e(tag, formatMsg(tag, msg));
        }
    }

    private static String formatMsg(String tag, String msg) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        if (null == stackTraceElements || stackTraceElements.length < 4) {
            return msg;
        }

        StringBuffer sb = new StringBuffer();
        String time = sf.format(new Date());

        sb.append(time).append(" | ");
        sb.append(tag).append(" | ");
        sb.append("Pid: ").append(android.os.Process.myPid()).append(" | ");
        sb.append("ThreadName: ").append(Thread.currentThread().getName()).append(" | ");
        sb.append("ThreadId: ").append(Thread.currentThread().getId()).append(" | ");
        sb.append("FileName: ").append(stackTraceElements[ORIGIN_STACK_INDEX].getFileName()).append(" | ");
        sb.append("ClassName: ").append(stackTraceElements[ORIGIN_STACK_INDEX].getClassName()).append(" | ");
        sb.append("MethodName: ").append(stackTraceElements[ORIGIN_STACK_INDEX].getMethodName()).append(" | ");
        sb.append("LineNumber: ").append(stackTraceElements[ORIGIN_STACK_INDEX].getLineNumber()).append(" | ");
        sb.append(msg);
//        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
//        if (stackTraceElements != null) {
//            int len = stackTraceElements.length;
//            for (int i = 0; i < len; i++) {
//                String FileName = "FileName StackTrace[" + i + "]" + Thread.currentThread().getStackTrace()[i].getFileName();
//                String ClassName = "ClassName StackTrace[" + i + "]" + Thread.currentThread().getStackTrace()[i].getClassName();
//                String MethodName = "MethodName StackTrace[" + i + "]" + Thread.currentThread().getStackTrace()[i].getMethodName();
//                String LineNumber = "LineNumber StackTrace[" + i + "]" + Thread.currentThread().getStackTrace()[i].getLineNumber();
//
//                Log.i(TAG,FileName);
//                Log.i(TAG,ClassName);
//                Log.i(TAG,MethodName);
//                Log.i(TAG,LineNumber);
//            }
//        }


        return sb.toString();
    }
}
