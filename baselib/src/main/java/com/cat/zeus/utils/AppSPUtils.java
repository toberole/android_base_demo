package com.cat.zeus.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSPUtils {
    private static final String FILE_NAME = "devapp_share";

    public synchronized static void save(String key, Object object) {
        String type = object.getClass().getSimpleName();
        SharedPreferences sp = AppUtil.getInstance().getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }

        editor.commit();
    }

    public synchronized static <T> T get(String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = AppUtil.getInstance().getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        Object res = null;
        if ("String".equals(type)) {
            res = sp.getString(key, defaultObject == null ? "" : (String) defaultObject);
        } else if ("Integer".equals(type)) {
            res = sp.getInt(key, defaultObject == null ? 0 : (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            res = sp.getBoolean(key, defaultObject == null ? false : (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            res = sp.getFloat(key, defaultObject == null ? 0 : (Float) defaultObject);
        } else if ("Long".equals(type)) {
            res = sp.getLong(key, defaultObject == null ? 0 : (Long) defaultObject);
        }

        return null == res ? null : (T) res;
    }
}
