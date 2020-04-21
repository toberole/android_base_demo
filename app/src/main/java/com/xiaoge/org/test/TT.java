package com.xiaoge.org.test;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class TT {
    public static final String TAG = TT.class.getSimpleName();

    public final void sys() {

        MyTask myTask = null;
        myTask.execute();
        myTask.cancel(true);

        HashSet hashSet = null;
        hashSet.add(new Object());

        String s = "abc";
        StringBuffer sb = new StringBuffer(s);
        sb = sb.reverse();

        StringBuilder sb1 = new StringBuilder(s);
        sb1 = sb1.reverse();

        s.toCharArray();

        Files files = null;

        Collection<String> collection;
        Collections.binarySearch(null, null);

        BlockingQueue<Object> bq = null;
        bq.poll();
        bq.remove();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Object> future = (Future<Object>) executorService.submit(new Runnable() {
            @Override
            public void run() {

            }
        });

        Callable<String> callable = null;
        FutureTask futureTask = new FutureTask(callable);


        List<String> list = new ArrayList<String>();
        list.add("abc");
        list.add("bbc");
        list.add("cbc");
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String str = it.next();
            System.out.println(str);
            if (str.equals("abc")) {
                it.remove();
            }
        }

        Looper.prepare();
        Looper.loop();

        HashMap hashMap = null;
        hashMap.put("", "");
        hashMap.get("");
        ConcurrentHashMap concurrentHashMap = null;
        concurrentHashMap.put("", "");


        String ss = null;

        Object object;

        Thread.dumpStack();

        // Objects.equals("","");

        String[] arrs = new String[]{"hello", "world"};
        List<String> list1 = Arrays.asList(arrs);
        list1.toArray(new String[0]);

        // Arrays.copyOf(null,0);

        ArrayList arrayList = new ArrayList();
        arrayList.add(null);

        Hashtable hashtable;
        HashMap hashMap1;

        LinkedHashMap<String, String> linkedHashMap = null;
        linkedHashMap.put("", "");

        HashSet hashSet1;
        hashSet.add(null);

        ReentrantLock reentrantLock = null;
        Condition condition = reentrantLock.newCondition();
        try {
            condition.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        condition.notify();
        condition.signal();

        Thread thread = null;
        thread.interrupt();
        ThreadLocal<Object> threadLocal = new ThreadLocal<>();
        threadLocal.set(new Object());
        threadLocal.get();
        threadLocal.remove();

        SoftReference<Object> softReference = new SoftReference<Object>(new Object(), new ReferenceQueue<>());

        Executors.callable(new Runnable() {
            @Override
            public void run() {

            }
        });

        AtomicStampedReference atomicStampedReference;
        AtomicInteger atomicInteger;
        CopyOnWriteArrayList copyOnWriteArrayList;
        ConcurrentLinkedQueue concurrentLinkedQueue;
        ArrayBlockingQueue arrayBlockingQueue;
        LinkedBlockingQueue linkedBlockingQueue;
        PriorityBlockingQueue priorityBlockingQueue;
        ConcurrentSkipListMap concurrentSkipListMap;

        AtomicInteger atomicInteger1;

        Thread thread1 = null;
        thread.interrupt();
        thread.stop();
        InterruptedException interruptedException;
    }

    private void test() {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

        BlockingQueue<String> bq = new ArrayBlockingQueue<>(0);

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                return String.valueOf(System.currentTimeMillis());
            }
        });

        future.cancel(true);

        // ForkJoinPool pool = new ForkJoinPool();

        Semaphore semaphore = new Semaphore(2);
        try {
            semaphore.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
        semaphore.release();


    }

    private static void test1() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                    cyclicBarrier.await();

                    System.out.println(System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e1) {
                    e1.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                    cyclicBarrier.await();

                    System.out.println(System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e1) {
                    e1.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                    cyclicBarrier.await();

                    System.out.println(System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e1) {
                    e1.printStackTrace();
                }
            }
        }).start();
    }

    public static void main1(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t.getName());
                e.printStackTrace(System.out);
            }
        });

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    List<String> lStrings = new ArrayList<String>();
//                    lStrings.add("hello");
//                    lStrings.add("world");
//
//                    Iterator<String> iterator = lStrings.iterator();
//                    while (iterator.hasNext()) {
//                        iterator.remove();
//                    }
//                } catch (Exception e) {
//
//                }
//
//            }
//        }).start();

        test1();
        System.out.println("end");
    }

    private static void test2() {
        AbstractQueuedSynchronizer abstractQueuedSynchronizer = null;
    }


    private void test3() {
        HandlerThread handlerThread = new HandlerThread("");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
    }

    private void test4() {
        Future<String> future = null;
        try {
            future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void test5() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                String res = String.valueOf(System.currentTimeMillis());
                if (true) throw new RuntimeException("hello world");
                return res;
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(callable);
        try {
            String res = future.get();
            System.out.println("res: " + res);
        } catch (Exception e) {
            System.out.println("error before");
            e.printStackTrace(System.out);
            System.out.println("error after");
        }
    }

    public static void main(String[] args) {
        test5();

        System.out.println("end");
    }
}
