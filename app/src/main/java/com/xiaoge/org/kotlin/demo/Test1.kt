package com.xiaoge.org.kotlin.demo

/**
 * 在 Kotlin 中，if是一个表达式，即它会返回一个值
 * if when for
 */
fun test_if() {
    var i = 10
    var ii = 20
    i = if (i > ii) i else ii
    println(i)

    // if的分支可以是代码块
    // 最后的表达式作为该块的值：
    val max = if (i > ii) {
        println("Choose i")
        i
    } else {
        println("Choose ii")
        ii
    }
    println(max)
}

/**
 * switch
 */
/**
 * when 既可以被当做表达式使用也可以被当做语句使用。如果它被当做表达式，
 * 符合条件的分支的值就是整个表达式的值，如果当做语句使用， 则忽略个别分支的值。
 * （像 if 一样，每一个分支可以是一个代码块，它的值 是块中最后的表达式的值。）
 * 如果其他分支都不满足条件将会求值 else 分支。 如果 when 作为一个表达式使用，
 * 则必须有 else 分支， 除非编译器能够检测出所有的可能情况都已经覆盖了。
 */
fun test_when(i: Int) {
    when (i) {
        1 -> {
            println("i: " + 1)
        }

        2 -> {
            println("i: " + 2)
        }

        else -> {// default
            println("default")
        }
    }
}

fun test_when1(i: Any) {
    when (i) {
        1, 2 -> {
            println("i: " + i)
        }

        Integer.parseInt("3") -> {// 表达式作为条件分支
            println("i: " + 3)
        }

        in 10..20 -> println("in 10..20")
        !in 30..40 -> println("!in 30..40")

        is String -> println("is String")

        else -> {// default
            println("default")
        }
    }
}

// 数组
/**
 *  1.使用arrayOf(), arrayOfNulls()，emptyArray()工具函数。
 *
 *  2.使用Array(size: Int, init:(Int) -> T)
 */
fun test_arr() {
    // 创建数组
    var arr1 = arrayOf(1, 2)

    // 创建指定大小的元素初始值为null的数组
    var arr2 = arrayOfNulls<String>(5)
    arr2.set(0, "hello")
    println(arr2[0])

    var int_arr = intArrayOf(1, 2, 3)
    var float_arr = floatArrayOf(1f, 2f, 3f)


}


fun main() {
    test_if()

    test_when(1)
    test_when(3)
    test_arr()
}

