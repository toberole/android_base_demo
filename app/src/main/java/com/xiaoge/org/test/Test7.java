package com.xiaoge.org.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Test7 {
    public static void main(String[] args) {
        String s = "abc";
//        String s = "abccccdd";
        int len = countLen(s);
        System.out.println("countLen len: " + len);
        len = countLen1(s);
        System.out.println("countLen1 len: " + len);
    }

    private static int countLen(String s) {
        Map<String, Integer> map = new HashMap<>();
        char[] chs = s.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            char ch = chs[i];
            boolean b = map.containsKey("" + ch);
            if (b) {
                int k = map.get("" + ch);
                map.put("" + ch, k + 1);
            } else {
                map.put("" + ch, 1);
            }
        }

        boolean b = false;
        int count1 = 0;
        int count2 = 0;
        for (Map.Entry<String, Integer> en : map.entrySet()) {
            int i = en.getValue();
            if (i == 1) {
                count1 = 1;
            } else {
                b = true;
                if (i % 2 == 1) {
                    count2 += i - 1;
                } else {
                    count2 += i;
                }
            }
        }


        return b ? count1 + count2 : 0;
    }

    private static int countLen1(String s) {
        Set<Character> set = new HashSet<>();
        int count = 0;
        char[] chs = s.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            if (set.contains(chs[i])) {
                count++;
                set.remove(chs[i]);
            } else {
                set.add(chs[i]);
            }
        }

        int ret;
        if (set.isEmpty()) {
            ret = count * 2;
        } else if (count == 0) {
            ret = 0;
        } else {
            ret = count * 2 + 1;
        }

        return ret;
    }
}
