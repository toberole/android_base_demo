package com.xiaoge.org.jni;

public class Test1 {
    public static native int sysHello(String str);

    static {
        System.loadLibrary("native-lib");
    }
}
