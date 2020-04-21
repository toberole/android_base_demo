package com.xiaoge.org.test;

import java.util.LinkedList;
import java.util.List;

public class Test11 {
    public static void main(String[] args) {
        List<Character> num1 = new LinkedList<>();
        num1.add('2');
        num1.add('4');
        num1.add('3');
        num1.add('5');

        List<Character> num2 = new LinkedList<>();
        num2.add('5');
        num2.add('6');
        num2.add('4');

        List<Character> res = new LinkedList<>();

        int len1 = num1.size();
        int len2 = num2.size();

        int k = Math.min(len1, len2);

        int n = 0;

        int i;
        for (i = 0; i < k; i++) {
            int i1 = num1.get(i) - '0';
            int i2 = num2.get(i) - '0';

            int sum = n + i1 + i2;
            n = sum / 10;
            res.add((char) (sum % 10 + '0'));
        }

        if (len1 > len2) {
            for (int j = i; j < len1; j++) {
                res.add(num1.get(j));
            }
        } else if (len1 < len2) {
            for (int j = i; j < len1; j++) {
                res.add(num2.get(j));
            }
        }

        for (i = 0; i < res.size(); i++) {
            System.out.println(res.get(i));
        }
    }
}
