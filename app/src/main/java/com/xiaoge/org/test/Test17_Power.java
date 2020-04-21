package com.xiaoge.org.test;

public class Test17_Power {
    public static void main(String[] args) {
        double d = 1.03;
        int e = 2;

        double ret = getPower(d, e);

        System.out.println("ret: " + ret);
    }

    public static double getPower(double base, int e) {
        if (e == 0) return 1;

        if (equal(base, 0)) throw new RuntimeException("base == 0");

        double res = getPower(base, e / 2);
        res *= res;

        if (e % 2 == 1) {
            res *= base;
        }

        return res;
    }

    public static boolean equal(double d1, double d2) {
        if (Math.abs(d1 - d2) < 0.000001) return true;
        return false;
    }
}
