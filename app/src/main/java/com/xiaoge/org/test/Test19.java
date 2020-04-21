package com.xiaoge.org.test;

import java.util.Stack;

public class Test19 {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack();
        int in[] = new int[]{1, 2, 3, 4, 5};
        // int out[] = new int[]{4, 5, 3, 2, 1};
        int out[] = new int[]{4, 3, 5, 2, 1};

        int len = out.length;
        int index = 0;
        for (int i = 0; i < in.length; i++) {
            if (in[i] == out[index]) {
                index++;
                continue;
            } else {
                stack.push(in[i]);
            }
        }

        if (stack.size() != 0) {
            while (!stack.isEmpty() && index < len) {
                if (stack.pop() == out[index++]) {
                    continue;
                } else {
                    break;
                }
            }
        }

        System.out.println(stack.size() > 0);

        System.out.println(3 | 9);
    }

    public static boolean isPopOrder(int[] pushA, int[] popA) {
        if (pushA.length == 0 || popA.length == 0)
            return false;
        Stack<Integer> s = new Stack<Integer>();
        //用于标识弹出序列的位置
        int popIndex = 0;
        for (int i = 0; i < pushA.length; i++) {
            s.push(pushA[i]);
            //如果栈不为空，且栈顶元素等于弹出序列
            while (!s.empty() && s.peek() == popA[popIndex]) {
                //出栈
                s.pop();
                //弹出序列向后一位
                popIndex++;
            }
        }
        return s.empty();
    }
}