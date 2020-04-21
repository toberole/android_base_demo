package com.xiaoge.org.jni;

public class Bean {
    public String tag;

    public Bean() {
    }

    public Bean(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public static int sysTag() {
        return 1;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "tag='" + tag + '\'' +
                '}';
    }
}
