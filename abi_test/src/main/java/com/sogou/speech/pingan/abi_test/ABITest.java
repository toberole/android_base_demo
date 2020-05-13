package com.sogou.speech.pingan.abi_test;

public class ABITest {
    public static native int init();

    static {
        System.loadLibrary("native-lib");
    }
}
