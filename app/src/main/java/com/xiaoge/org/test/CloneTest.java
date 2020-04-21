package com.xiaoge.org.test;

public class CloneTest implements Cloneable {
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * 最终返回值 1
     */
    public static int test() {
        try {
            return 0;
        } finally {
            return 1;
        }
    }

    public static void main(String[] args) {
        System.out.println(test());
    }
}
