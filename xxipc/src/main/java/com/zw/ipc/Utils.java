package com.zw.ipc;

import java.lang.reflect.Method;

public class Utils {
    public static String getMethodSignature(Method m) {
        StringBuffer sb = new StringBuffer();
        String methodName = m.getName();
        String params = getMethodParameters(m.getParameterTypes());
        sb.append(methodName).append(params);
        return sb.toString();
    }

    public static String getMethodParameters(Class<?>[] parameterTypes) {
        if (parameterTypes == null || parameterTypes.length == 0) return null;

        StringBuffer sb = new StringBuffer();
        for (Class clazz : parameterTypes) {
            String clazzName = getClassName(clazz);
            sb.append(clazzName).append(",");
        }

        return sb.toString();
    }

    public static String getClassName(Class clazz) {
        if (clazz == Boolean.class) {
            return "boolean";
        } else if (clazz == Byte.class) {
            return "byte";
        } else if (clazz == Character.class) {
            return "char";
        } else if (clazz == Short.class) {
            return "short";
        } else if (clazz == Integer.class) {
            return "int";
        } else if (clazz == Long.class) {
            return "long";
        } else if (clazz == Float.class) {
            return "float";
        } else if (clazz == Double.class) {
            return "double";
        } else if (clazz == Void.class) {
            return "void";
        } else {
            return clazz.getName();
        }
    }


}
