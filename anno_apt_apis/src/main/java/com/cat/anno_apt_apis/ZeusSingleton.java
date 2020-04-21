package com.cat.anno_apt_apis;

public class ZeusSingleton {
    private static final String TAG = ZeusSingleton.class.getSimpleName();

    public static ZeusSingleton getInstance() {
        return ZeusSingletonHolder.instance;
    }

    public <T> T getInstance(Class clazz) {
        return null;
    }

    private ZeusSingleton() {

    }

    private static class ZeusSingletonHolder {
        public static ZeusSingleton instance = new ZeusSingleton();
    }
}