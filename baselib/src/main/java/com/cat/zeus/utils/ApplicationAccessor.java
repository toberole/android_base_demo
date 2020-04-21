package com.cat.zeus.utils;

import android.app.Application;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ApplicationAccessor {
    private static Application app;

    public Application getApplication() {
        if (app == null) {
            try {
                Class activityThreadClazz = Class.forName("android.app.ActivityThread");
                Method method = activityThreadClazz.getMethod("currentActivityThread");
                Object activityThreadObj = method.invoke(activityThreadClazz, new Object[0]);
                Class activityThreadCls = activityThreadObj.getClass();
                Field field = activityThreadCls.getDeclaredField("mInitialApplication");
                field.setAccessible(true);
                app = (Application) field.get(activityThreadObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return app;
    }

    private ApplicationAccessor() {
    }

    private static class ApplicationAccessorHolder {
        public static ApplicationAccessor instance = new ApplicationAccessor();
    }

    public static ApplicationAccessor getInstance() {
        return ApplicationAccessorHolder.instance;
    }
}
