package com.xiaoge.org.test;

import java.util.concurrent.CountDownLatch;

public class VolatileTest {
    public volatile int inc = 0;

    public void increase() {
        inc++;
    }

    public static void main(String[] args) {
        int count = 10;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        final VolatileTest test = new VolatileTest();
        for (int i = 0; i < count; i++) {
            new Thread() {
                public void run() {
                    for (int j = 0; j < 1000; j++)
                        test.increase();

                    countDownLatch.countDown();
                }
            }.start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(test.inc);
    }
}
