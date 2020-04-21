package com.xiaoge.org.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();

    private ThreadPoolExecutor executor;

    private Context context;
    private CrashListener listener;
    private File logFileDir;

    public void init(@NonNull Context context, File logFileDir, CrashListener listener) {
        this.context = context;
        this.logFileDir = logFileDir;
        this.listener = listener;
        this.executor = new ThreadPoolExecutor(3, 3,
                30, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
        this.executor.allowCoreThreadTimeOut(true);
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void init(@NonNull Context context) {
        this.context = context;
        this.executor = new ThreadPoolExecutor(3, 3,
                30, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
        this.executor.allowCoreThreadTimeOut(true);
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(final Thread thread, final Throwable ex) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (logFileDir == null) {
                    logFileDir = Environment.getExternalStorageDirectory();
                }
                File logFile = new File(logFileDir, getFormatDate() + "_log.crash");

                writeLog_(logFile, "CrashHandler", ex.getMessage(), ex);
                if (listener != null) {
                    listener.onCrash(thread, ex, logFile.getAbsolutePath());
                } else {
                    defaultHandler.uncaughtException(thread, ex);
                }
            }
        });
    }

    public void writeLog(File logFile, String tag, String message, Throwable ex) {
        FileWriter fileWriter = null;
        BufferedWriter bufdWriter = null;
        PrintWriter printWriter = null;
        try {
            fileWriter = new FileWriter(logFile, true);
            bufdWriter = new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(fileWriter);
            bufdWriter.append(buildTitle(context));
            bufdWriter.append(buildBody(context));
            bufdWriter.append("\r\n" + getFormatDate()).append(" ").append("E").append('/').append(tag).append(" ")
                    .append(message).append('\n');
            bufdWriter.flush();

            ex.printStackTrace(printWriter);

            printWriter.flush();
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            IOUtil.closeQuietly(fileWriter, bufdWriter, printWriter);
        }
    }

    public void writeLog_(File logFile, String tag, String message, Throwable ex) {
        FileOutputStream fout = null;
        PrintStream printStream = null;
        try {
            fout = new FileOutputStream(logFile, true);
            fout.write(buildTitle(context).getBytes());
            fout.flush();
            fout.write(buildBody(context).getBytes());
            fout.flush();
            fout.write(("\r\n" + getFormatDate() + " " + "E" + '/' + tag + " " + message + '\n').getBytes());
            fout.flush();

            printStream = new PrintStream(fout);
            ex.printStackTrace(printStream);

            printStream.flush();
            fout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            IOUtil.closeQuietly(fout, printStream);
        }
    }

    private String buildTitle(Context context) {
        return "Crash Log: " + context.getPackageManager().getApplicationLabel(context.getApplicationInfo()) + "\r\n";
    }

    private String buildBody(Context context) {
        StringBuilder sb = new StringBuilder();

        sb.append("APPLICATION INFORMATION").append('\n');
        PackageManager pm = context.getPackageManager();
        ApplicationInfo ai = context.getApplicationInfo();
        sb.append("Application : ").append(pm.getApplicationLabel(ai)).append('\n');

        try {
            PackageInfo pi = pm.getPackageInfo(ai.packageName, 0);
            sb.append("Version Code: ").append(pi.versionCode).append('\n');
            sb.append("Version Name: ").append(pi.versionName).append('\n');
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        sb.append('\n').append("DEVICE INFORMATION").append('\n');
        sb.append("Board: ").append(Build.BOARD).append('\n');
        sb.append("BOOTLOADER: ").append(Build.BOOTLOADER).append('\n');
        sb.append("BRAND: ").append(Build.BRAND).append('\n');
        sb.append("CPU_ABI: ").append(Build.CPU_ABI).append('\n');
        sb.append("CPU_ABI2: ").append(Build.CPU_ABI2).append('\n');
        sb.append("DEVICE: ").append(Build.DEVICE).append('\n');
        sb.append("DISPLAY: ").append(Build.DISPLAY).append('\n');
        sb.append("FINGERPRINT: ").append(Build.FINGERPRINT).append('\n');
        sb.append("HARDWARE: ").append(Build.HARDWARE).append('\n');
        sb.append("HOST: ").append(Build.HOST).append('\n');
        sb.append("ID: ").append(Build.ID).append('\n');
        sb.append("MANUFACTURER: ").append(Build.MANUFACTURER).append('\n');
        sb.append("PRODUCT: ").append(Build.PRODUCT).append('\n');
        sb.append("TAGS: ").append(Build.TAGS).append('\n');
        sb.append("TYPE: ").append(Build.TYPE).append('\n');
        sb.append("USER: ").append(Build.USER).append("\r\n");

        return sb.toString();
    }

    private static String getFormatDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return simpleDateFormat.format(new Date());
    }

    public static CrashHandler getInstance() {
        return CrashHandlerHolder.instance;
    }

    private static class CrashHandlerHolder {
        public static CrashHandler instance = new CrashHandler();
    }

    public static interface CrashListener {
        void onCrash(Thread thread, Throwable ex, String savedLogFilePath);
    }
}
