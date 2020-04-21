package com.xiaoge.org.kotlin.demo

// Lambda 表达式
// lambda 表达式总是被大括号括着,完整语法形式的参数声明放在括号内,并有可选的类型标注，
// 函数体跟在一个 -> 符号之后。如果推断出的该 lambda 的返回类型不是 Unit，那么该
// lambda 主体中的最后一个（或可能是单个）表达式会视为返回值。

var sum = { a: Int, b: Int -> a + b }

fun test_lambda() {
    var arr = arrayOf(1, 2, 3)
    arr.filter {
        var b = it > 0
        b
    }

    // 使用限定的返回语法从 lambda 显式返回一个值
    arr.filter {
        var b = it > 0
        return@filter b
    }

    // 匿名函数
    fun(x: Int, y: Int): Int = x + y
}



fun main() {
    var i = sum(1, 2)
    println(i)
}