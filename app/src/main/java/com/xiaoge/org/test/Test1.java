package com.xiaoge.org.test;

import java.util.Stack;

/**
 * 两个栈模拟一个队列
 */
public class Test1 {

    public static void main(String[] args) {
        test2();
    }

    private static void test2() {
        Queue queue = new Queue();
        queue.put("hello");
        queue.put("world");

        queue.put("*********");

        System.out.println(queue.take());
        System.out.println(queue.take());
        queue.put("good");
        System.out.println(queue.take());
        System.out.println(queue.take());
    }

    private static void test1() {
        Queue queue = new Queue();
        queue.put("hello");
        queue.put("world");

        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
    }


    private static class Queue {
        private static Stack<String> in = new Stack<>();
        private static Stack<String> out = new Stack<>();

        public static boolean put(String s) {
            return in.add(s);
        }

        public static String take() {
            String res;

            if (out.empty()) {
                if (!in.empty()) {
                    while (!in.empty()) {
                        out.push(in.pop());
                    }

                    res = out.pop();
                } else {
                    res = null;
                }
            } else {
                res = out.pop();
            }
            return res;
        }
    }
}
