package com.xiaoge.org.test;

public class Test8_Palindrome {
    private static int index;
    private static int len;

    public static String palindrome(String s) {
        if (s.length() < 2) return s;

        for (int i = 0; i < s.length(); i++) {
            count(s, i, i);
            count(s, i, i + 1);
        }

        return s.substring(index, index + len);
    }

    private static void count(String s, int l, int r) {
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }

        if (len < r - l - 1) {
            len = r - l - 1;
            index = l + 1;
        }
    }

    public static void main(String[] args) {
        String s = "babad";
        System.out.println("s: " + palindrome(s));
    }
}
