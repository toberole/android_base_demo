package com.xiaoge.org.test;

public class Test9 {
    public static void main(String[] args) {
        String s = "((()))";

        int n = deep(s);

        System.out.println(n);
    }

    private static int deep(String s) {
        int ret = 0;
        int temp = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                temp++;
            } else {
                ret = Math.max(ret, temp);
                temp = 0;
            }
        }
        return ret;
    }
}
