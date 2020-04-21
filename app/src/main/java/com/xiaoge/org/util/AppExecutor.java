package com.xiaoge.org.util;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AppExecutor {
    private static ThreadPoolExecutor executor = null;
    private static ThreadPoolExecutor linearExecutor = null;

    public void execute(Runnable runnable) {
        if (null != runnable) {
            execute(runnable, false);
        }
    }

    public void execute(Runnable runnable, boolean isLinear) {
        if (null != runnable) {
            if (isLinear) {
                linearExecutor.execute(runnable);
            } else {
                executor.execute(runnable);
            }
        }
    }

    public ThreadPoolExecutor getExecutor() {
        return executor;
    }

    public ThreadPoolExecutor getLinearExecutor() {
        return linearExecutor;
    }

    public void shutdown() {
        if (null != executor) {
            executor.shutdown();
        }
    }

    public void shutdownNow() {
        if (null != executor) {
            executor.shutdownNow();
        }
    }

    private AppExecutor() {
        executor = new ThreadPoolExecutor(10, 100,
                60, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
        executor.allowCoreThreadTimeOut(true);

        linearExecutor = new ThreadPoolExecutor(1, 1,
                60, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
        linearExecutor.allowCoreThreadTimeOut(true);
    }

    private static class ExecutorHolder {
        public static AppExecutor executor = new AppExecutor();
    }

    public static AppExecutor getInstance() {
        return ExecutorHolder.executor;
    }
}
