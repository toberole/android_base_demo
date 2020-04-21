package com.xiaoge.org.jbase;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class Test {
    public void test() {
        Integer integer = Integer.parseInt("");

        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("hello");
        threadLocal.get();

        Thread thread = null;

        AbstractQueuedSynchronizer synchronizer;

        AtomicReference atomicReference = null;
        atomicReference.set(null);
        atomicReference.compareAndSet(null, null);

    }

    /**
     * 为了节省内存，对于下列包装对象的两个实例，当它们的基本值相同时，他们总是==：
     * Boolean (全部缓存)
     * Byte (全部缓存)
     * Character (<= 127缓存)
     * Integer(-128 — 127)
     * Short(-128 — 127缓存)
     * Long(-128 — 127缓存)
     */
    public static void test1() {
        Integer i1 = 11;
        Integer i2 = 11;

        Integer i3 = 250;
        Integer i4 = 250;

        Integer i5 = -128;
        Integer i6 = -128;

        Integer i7 = 127;
        Integer i8 = 127;

        Integer i9 = new Integer(127);
        Integer i10 = new Integer(127);

        System.out.println("i1 == i2: " + (i1 == i2));
        System.out.println("i3 == i4: " + (i3 == i4));
        System.out.println("i5 == i6: " + (i5 == i6));
        System.out.println("i7 == i8: " + (i7 == i8));
        System.out.println("i9 == i10: " + (i9 == i10));

        Boolean b1 = false;
        Boolean b2 = false;
        System.out.println("b1 == b2: " + (b1 == b2));

        Byte byte1 = 1;
        Byte byte2 = 1;
        System.out.println("byte1 == byte2: " + (byte1 == byte2));

        Character c1 = 'C';
        Character c2 = 'C';
        System.out.println("c1 == c2: " + (c1 == c2));

        Short s1 = 127;
        Short s2 = 127;
        System.out.println("s1 == s2: " + (s1 == s2));

        Long l1 = 127l;
        Long l2 = 127l;
        System.out.println("l1 == l2: " + (l1 == l2));

    }

    public static void test2() {
        Short cache[] = new Short[-(-128) + 127 + 1];

        for (int i = 0; i < cache.length; i++)
            cache[i] = new Short((short) (i - 128));

        for (int i = 0; i < cache.length; i++) {
            System.out.print(cache[i] + " ");
        }
    }

    public static void test3() {
        AtomicReference<String> s = new AtomicReference<>();
        s.set("hello");
        // boolean b = s.compareAndSet("hello", "world");
        boolean b = s.compareAndSet("hello1", "world");

        System.out.println("b: " + b);
    }

    public static void main(String args[]) {
        test3();
    }
}
