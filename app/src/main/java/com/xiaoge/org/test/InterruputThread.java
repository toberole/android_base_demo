package com.xiaoge.org.test;


import java.util.concurrent.TimeUnit;

public class InterruputThread {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    //判断当前线程是否被中断
                    if (this.isInterrupted()) {
                        System.out.println("线程中断");
                        break;
                    }
                }

                System.out.println("已跳出循环,线程中断!" + interrupted());
            }
        };
        t1.start();
        TimeUnit.SECONDS.sleep(2);
        t1.interrupt();

        /**
         * 输出结果:
         线程中断
         已跳出循环,线程中断!
         */
    }
}
