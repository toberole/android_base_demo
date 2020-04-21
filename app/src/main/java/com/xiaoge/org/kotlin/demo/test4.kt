package com.xiaoge.org.kotlin.demo


// 集合
// 集合分为可变集合和不可变集合
/*
    List/MutableList：

    Set/MutableSet；

    Map/MutableMap；

    Collection/MutableCollection；

    Iterable/MutableIterable；

 */
fun test4_1() {
    var list = listOf(1, 2, 3, 4)
    var b = list.any() {
        it % 2 == 0// 只要有一元素满足就是true
    }
    println(b)

    var b1 = list.all {
        it % 2 == 0//所有的元素满足返回true
    }
    println(b1)

    var count = list.count {
        it % 2 == 0//统计符合条件的元素的个数
    }
    println(count)

    // 从第一项到最后一项
    var total = list.fold(0/*初始值*/, { total, next -> total + next })
    println(total)
    // 从最后一项到第一项
    var total1 = list.foldRight(0/*初始值*/, { total, next -> total + next })
    println(total1)

    // 遍历所有元素
    list.forEach { println(it) }
}

fun main() {
    test4_1()
}