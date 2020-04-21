package com.xiaoge.org.test;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.ViewGroup;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

import dalvik.system.PathClassLoader;

public class TT1 {
    public void test2() {
        ProcessBuilder builder = new ProcessBuilder();
//        builder.start();
    }

    public void test() {
        HandlerThread handlerThread = new HandlerThread("");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        handler = new Handler();
        Message msg = Message.obtain();
        handler.sendMessage(msg);
        Looper.prepare();
        Looper.loop();
        AsyncTask asyncTask = null;
        asyncTask.execute(null, null);

        CountDownLatch countDownLatch;
        CyclicBarrier cyclicBarrier;
        Semaphore semaphore = new Semaphore(1);
        try {
            semaphore.acquire(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(1);
        }

        AbstractQueuedSynchronizer abstraceuedSynchronizer;

        PathClassLoader pathClassLoader;

        ViewGroup viewGroup;

        Integer integer = Integer.parseInt("");
    }

    /**
     * 软/弱引用可以和一个引用队列（ReferenceQueue）联合使用，如果软引用所引用的对象被垃圾回收器回收，
     * Java虚拟机就会把这个软引用加入到与之关联的引用队列中。
     * 利用这个队列可以得知被回收的软/弱引用的对象列表，从而为缓冲器清除已失效的软/弱引用。
     */
    public Map<SoftReference<String>, Integer> map = new HashMap<>();
    public ReferenceQueue<String> referenceQueue = new ReferenceQueue<String>();

    public void test1() {
        SoftReference<String> o1 = new SoftReference<>("hello", referenceQueue);
        map.put(o1, 1);
        SoftReference<String> o2 = new SoftReference<>("world", referenceQueue);
        map.put(o2, 2);
    }

    public boolean isStop = false;

    public void clearInvalidValue() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop) {
                    try {
                        SoftReference<String> o = (SoftReference<String>) referenceQueue.remove();
                        if (o != null) {
                            map.remove(o);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
