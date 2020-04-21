package com.xiaoge.org.kotlin.demo

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis
import kotlin.io.println as println1

/*
    扩展函数形式
        fun 类名.方法名():返回类型 = 方法体
*/
open class A {
    open fun test() {
        println1("A#test")
    }
}

suspend fun test6_1() {
    delay(100)
}

fun main1() {
    runBlocking {
        GlobalScope.launch { test6_1() }
        kotlin.io.println("... main ...")
    }
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}


fun main6_1() = runBlocking<Unit> {
    //sampleStart
    val time = measureTimeMillis {
        val one = doSomethingUsefulOne()
        val two = doSomethingUsefulTwo()
        println1("The answer is ${one + two}")
    }
    println1("Completed in $time ms")
    //sampleEnd
}

// 使用 async 并发
fun main6_2() = runBlocking<Unit> {
    //sampleStart
    val time = measureTimeMillis {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        println1("The answer is ${one.await() + two.await()}")
    }
    println1("Completed in $time ms")
    //sampleEnd
}

// 惰性启动的 async
// 使用一个可选的参数 start 并传值 CoroutineStart.LAZY，可以对 async 进行惰性操作。
// 只有当结果需要被 await 或者如果一个 start 函数被调用，协程才会被启动。
fun main() = runBlocking<Unit> {
    //sampleStart
    val time = measureTimeMillis {
        val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
        val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
        // some computation
        one.start() // start the first one
        two.start() // start the second one
        println1("The answer is ${one.await() + two.await()}")
    }
    println1("Completed in $time ms")
    //sampleEnd
}


