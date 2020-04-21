package com.xiaoge.org.test;

import java.util.Arrays;

public class Test6 {
    public static void main(String[] args) {
        // String[] strs = {"customer", "car", "cat"};
        String[] strs = {"flower", "flow", "flight"};

        StringBuffer sb = new StringBuffer();

        Arrays.sort(strs);

        String s1 = strs[0];
        String s2 = strs[strs.length - 1];

        System.out.println("s1: " + s1);
        System.out.println("s2: " + s2);

        char[] chs1 = s1.toCharArray();
        char[] chs2 = s2.toCharArray();

        int m = s1.length();
        int n = s2.length();

        System.out.println("m: " + m);
        System.out.println("n: " + n);

        int k = Math.min(m, n);
        System.out.println("k: " + k);

        for (int i = 0; i < k; i++) {
            if (chs1[i] == chs2[i]) {
                sb.append(chs1[i]);
            } else {
                break;
            }
        }

        System.out.println(sb.toString());
    }
}
