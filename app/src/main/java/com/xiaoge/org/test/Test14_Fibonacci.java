package com.xiaoge.org.test;

public class Test14_Fibonacci {
    public static void main(String[] args) {
        System.out.println(fibonacci(6));
        System.out.println(fibonacci_1(6));
        long start_time = System.currentTimeMillis();
        int ret = fibonacci(39);
        long end_time = System.currentTimeMillis();
        System.out.println("time: " + (end_time - start_time) + " ret: " + ret);

        start_time = System.currentTimeMillis();
        ret = fibonacci_1(39);
        end_time = System.currentTimeMillis();

        System.out.println("time: " + (end_time - start_time) + " ret: " + ret);

    }

    public static int fibonacci(int n) {
        if (n == 1) return 1;
        if (n == 2) return 1;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static int fibonacci_1(int n) {
        if (n == 1 || n == 2) return 1;

        int first = 1;
        int second = 1;
        int ret = 0;
        for (int i = 3; i <= n; i++) {
            ret = first + second;
            first = second;
            second = ret;
        }
        return ret;
    }
}
