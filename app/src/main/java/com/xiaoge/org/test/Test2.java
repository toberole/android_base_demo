package com.xiaoge.org.test;


import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Test2 {
    private static void test1() {
        Semaphore semaphore = new Semaphore(1);
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void test2() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        System.out.println(System.currentTimeMillis());

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread1 start: " + System.currentTimeMillis());

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("thread1 end" + System.currentTimeMillis());

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread2 start");

                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("thread2: " + System.currentTimeMillis());
                throw new RuntimeException("hello test");
            }
        }).start();
    }

    public static void main(String[] args) {
        test2();
    }
}