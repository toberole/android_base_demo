package com.xiaoge.org.jni;

public class Test {

    /**
     * 静态注册
     */
    public native void test(byte b, char ch, short s, int i, float f, double d, String str, Object o);

    /**
     * 动态注册
     */
    public native void test1(byte b);

    /**
     * 动态注册
     */
    public native void test2(int i, Object o);

    static {
        System.loadLibrary("native-lib");
    }
}
