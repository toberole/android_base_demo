package com.cat.zeus.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String formatDate(long time) {
        return formatDate(new Date(time));
    }

    public static String formatDate(Date date) {
        return formatDate(date, null);
    }

    public static String formatDate(Date date, String pattern) {
        if (TextUtils.isEmpty(pattern)) {
            pattern = "yyyy-MM-dd-HH-mm-ss";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static String getFormatDate() {
        return getFormatDate(null);
    }

    public static String getFormatDate(String pattern) {
        if (TextUtils.isEmpty(pattern)) {
            pattern = "yyyy-MM-dd-HH-mm-ss";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
    }
}
