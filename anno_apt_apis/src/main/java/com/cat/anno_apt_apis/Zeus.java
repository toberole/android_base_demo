package com.cat.anno_apt_apis;

import android.app.Activity;

import java.lang.reflect.Constructor;

import androidx.annotation.NonNull;

public class Zeus {
    public static <T extends Activity> void bind(@NonNull T target) {
        try {
            Class clazz = target.getClass();
            String clazzname = clazz.getName();
            String targetClazzName = clazzname + "ViewBinding";
            Class targetClazz = Class.forName(targetClazzName);
            Constructor constructor = targetClazz.getConstructor(clazz);
            constructor.newInstance(target);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Zeus() {
    }
}
