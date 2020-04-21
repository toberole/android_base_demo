package com.xiaoge.org.jni;

import com.xiaoge.org.util.LogUtil;

public class Data {

    public native void test();

    public static native void test1();

    /**
     * 传递基础数据类型
     */
    public static native void test2(byte b, char ch, short s, int i, long l, float f, double d, boolean bl);

    /**
     * 传递类
     */
    public static native void test3(Bean bean);

    /**
     * 传递 String
     */
    public static native void test4(String s);

    /**
     * 传递 数组
     * 底层处理 修改java层数组
     */
    public static native void test5(int arr[], int len);

    /**
     * 传递 String数组
     */
    public static native void test5_1(String arr[]);

    /**
     * 传递 byte数组
     */
    public static native void test5_2(byte arr[]);

    /**
     * 传递 char 数组
     */
    public static native void test5_3(char arr[]);

    /**
     * 传递 callback
     */
    public static native void test5_4(Object cb);

    /**
     * 传递 callback
     */
    public static native void test5_4_1(Callback cb);

    /**
     * 传递 枚举
     */
    public static native void test5_5(Gender gender);

    /**
     * 返回值 byte
     */
    public static native byte test6();

    /**
     * 返回值 char
     */
    public static native char test7();

    /**
     * 返回值 int
     */
    public static native int test8();

    /**
     * 返回值 String
     */
    public static native String test9();

    /**
     * 返回值 int[]
     */
    public static native int[] test10();


    /**
     * 返回值 java 对象
     */
    public static native Bean test11();

    /**
     * 返回值 java 对象
     */
    public static native Bean test12();

    /**
     * Push/PopLocalFrame 管理jni局部变量
     */
    public static native void test13();

    /**
     * 获取线程的JNIEnv
     */
    public static native void test14();

    public static native void test14_1();

    public static native void test_fork();

    public static native void native_load_so();

    public interface Callback {
        void onError(int errorcode, String errormsg);

        void onCallback_Short(int id, short[] data);

        void onCallback_byte(int id, byte[] data);
    }

    /*
     * Class:     com_xiaoge_org_jni_Data
     * Method:    callback
     * Signature: (ILjava/lang/String;)V
     */
    public static void callback(int code, String msg) {
        LogUtil.i("jni-log", "Data#callback code: " + code + " msg: " + msg);
    }


    static {
        System.loadLibrary("native-lib");
    }
}
