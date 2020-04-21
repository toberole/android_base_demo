package com.xiaoge.org.test;

import java.util.concurrent.TimeUnit;

public class InterruputSleepThread3 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        TimeUnit.SECONDS.sleep(2);
                    }
                } catch (InterruptedException e) {
                    System.out.println("Interruted When Sleep");
                    boolean interrupt = this.isInterrupted();
                    System.out.println("interrupt:" + interrupt);
                }
            }
        };
        t1.start();
        TimeUnit.SECONDS.sleep(2);
        t1.interrupt();

        /**
         * 输出结果:
         *   Interruted When Sleep
         *   interrupt:false
         */
    }
}
