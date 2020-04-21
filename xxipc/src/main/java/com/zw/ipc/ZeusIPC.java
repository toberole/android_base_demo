package com.zw.ipc;

import android.content.Context;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class ZeusIPC {
    //得到对象
    public static final int TYPE_NEW = 0;
    //得到单例
    public static final int TYPE_GET = 1;

    private ConcurrentHashMap<String, Class<?>> annotatedClasses = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Class<?>, ConcurrentHashMap<String, Method>> rawMethods = new ConcurrentHashMap<>();

    public void connect(Context context, String packageName,Class<? extends ZeusService> serviceClass) {
        ZeusServiceConnectionManager.getInstance().bindService(context,packageName,serviceClass);
    }

    public void register(Class<?> clazz) {
        registerClass(clazz);
        registerMethod(clazz);
    }

    private void registerMethod(Class<?> clazz) {
        ConcurrentHashMap<String, Method> map = rawMethods.get(clazz);
        if (map == null) {
            map = new ConcurrentHashMap<String, Method>();
            rawMethods.putIfAbsent(clazz, map);
        }

        Method[] methods = clazz.getMethods();
        if (methods != null && methods.length > 0) {
            for (Method m : methods) {
                String key = Utils.getMethodSignature(m);
                map.putIfAbsent(key, m);
            }
        }
    }

    private void registerClass(Class<?> clazz) {
        String className = clazz.getName();
        if (!annotatedClasses.containsKey(className)) {
            annotatedClasses.putIfAbsent(className, clazz);
        }
    }

    public static ZeusIPC getInstance() {
        return ZeusIPCHolder.zeusIpc;
    }

    private ZeusIPC() {

    }

    private static class ZeusIPCHolder {
        public static ZeusIPC zeusIpc = new ZeusIPC();
    }
}
