package com.xiaoge.org.jni;

public enum Gender {
    Man(0), Woman(1);
    int value;

    Gender(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
