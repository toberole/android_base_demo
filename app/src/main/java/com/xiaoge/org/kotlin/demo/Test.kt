package com.xiaoge.org.kotlin.demo

/**
 * 变量
 */
var s_: String = ""

// 字符串
fun test() {
    var str: String;
    str = "hello string"
    for (c in str) {
        println(c)
    }

    val s: String = "hello val str"
    // $
    println("val str = $s")
}

// 操作符
fun test1() {
//    shl 相当于Java中的左移运算符 <<
//    shr 相当于Java中的右移运算符 >>
//    ushr 无符号右移，高位补0 >>>
//    and 按位与操作 &
//    or 按位或操作 |
//    xor 按位异或操作 ^
//    inv 按位取反 ~
}

// 使用字符串模板
fun test2() {
    var a = 1
    // 模板中的简单名称：
    val s1 = "a is $a"

    a = 2
    // 模板中的任意表达式：
    val s2 = "${s1.replace("is", "was")}, but now is $a"
    println(s2)
}

fun test3() {
    var arr = arrayOf(1, 2, 3, 4, 5)
    for ((index, value) in arr.withIndex()) {
        println("index = $index, value = $value")
    }
}

fun main() {
    println("hello main")

    // 变量
    var i = 10
    println(i)
    // 定义变量 显式指定类型
    var a: Int = 1

    // 常量
    val ii = 11;
    println(ii)

    var name: String;
    name = "hello name"
    // 错误写法 不能为空
    // name = null;

    // 可以为空?
    var name1: String?
    name1 = "hello name1"
    name1 = null

    test()

    test2()

    test3()
}

