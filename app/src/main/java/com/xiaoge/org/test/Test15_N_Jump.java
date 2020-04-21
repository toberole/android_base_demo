package com.xiaoge.org.test;

public class Test15_N_Jump {
    public static void main(String[] args) {
        int ret = jump(5);
        System.out.println("ret: " + ret);

        ret = jump1(5);
        System.out.println("ret: " + ret);
    }

    /**
     * 一次跳一阶或者是二阶
     */
    private static int jump(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;

        int n1 = 1;
        int n2 = 2;
        int ret = 0;
        for (int i = 3; i <= n; i++) {
            ret = n1 + n2;
            n1 = n2;
            n2 = ret;
        }
        return ret;
    }

    private static int jump1(int n) {
        return 1 << n - 1;
    }

}
