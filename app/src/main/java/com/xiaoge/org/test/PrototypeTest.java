package com.xiaoge.org.test;

//具体原型类
class Realizetype implements Cloneable {
    public String s;

    Realizetype(String s) {
        this.s = s;
        System.out.println("具体原型创建成功！");
    }

    public Object clone() throws CloneNotSupportedException {
        System.out.println("具体原型复制成功！");
        return (Realizetype) super.clone();
    }
}

public class PrototypeTest {
    public static void main(String[] args) throws CloneNotSupportedException {

        Realizetype obj1 = new Realizetype("hello");

        Realizetype obj2 = (Realizetype) obj1.clone();

        System.out.println("obj1==obj2 ? " + (obj1 == obj2));

        System.out.println(obj1.s);
        System.out.println(obj2.s);
    }
}