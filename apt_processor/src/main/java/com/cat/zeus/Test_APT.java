package com.cat.zeus;


/**
 * element是代表程序的一个元素，这个元素可以是：包、类/接口、属性变量、方法/方法形参、泛型参数。
 * element是java-apt(编译时注解处理器)技术的基础
 * <p>
 * 包：PackageElement
 */
public class Test_APT<E> {
    /**
     * 类、接口、注解都属于TypeElement
     * Test_APT -> TypeElement
     * E -> TypeParameterElement
     * n[成员变量] -> VariableElement
     * sys[方法] -> ExecutableElement
     */

    public int n;

    public void sys(String hello/* 参数VariableElement */) {

    }

}
