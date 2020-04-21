package com.xiaoge.org.test;

public class Test10 {
    public static void main(String[] args) {
        String s = "1234";

        int num = str2Num(s);

        System.out.println("num: " + num);
    }

    private static int str2Num(String s) {
        int ret = 0;

        int len = s.length();
        for (int i = len - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            if (ch >= '0' && ch <= '9') {
                int temp = ch - '0';
                if (i > 0) {
                    for (int j = 0; j < i; j++) {
                        temp *= 10;
                    }

                    ret += temp;
                } else {
                    ret += temp;
                }

            } else {
                ret = 0;
                break;
            }
        }
        return ret;
    }

    public static int StrToInt(String str) {
        if (str.length() == 0)
            return 0;
        char[] chars = str.toCharArray();
        int flag = 0;
        if (chars[0] == '+') {
            flag = 1;
        } else if (chars[0] == '-') {
            flag = 2;
        }

        int start = flag > 0 ? 1 : 0;

        int res = 0;
        for (int i = start; i < chars.length; i++) {
            if (Character.isDigit(chars[i])) {
                int temp = chars[i] - '0';
                res = res * 10 + temp;
            } else {
                return 0;
            }
        }
        return flag != 2 ? res : -res;
    }
}
