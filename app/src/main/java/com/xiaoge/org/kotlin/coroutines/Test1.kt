package com.xiaoge.org.kotlin.coroutines

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

// 协程调度器

fun main_test1() {
    runBlocking {
        launch {
            // 运行在父协程的上下文中，即 runBlocking 主协程
            println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Unconfined) {
            // 不受限的——将工作在主线程中
            println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Default) {
            // 将会获取默认调度器
            println("Default               : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(newSingleThreadContext("MyOwnThread")) {
            // 将使它获得一个新的线程
            println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
        }
    }
}

fun test_c1(): Int {
    return 1
}

fun test_c2(): Int {
    return 2
}

fun test_c0() {
    runBlocking {
        var a = null
        var b = null
    }
}

//子协程
//当一个协程被其它协程在 CoroutineScope 中启动的时候， 它将通过 CoroutineScope.coroutineContext 来承袭上下文，并且这个新协程的 Job 将会成为父协程作业的 子 作业。当一个父协程被取消的时候，所有它的子协程也会被递归的取消。
//当 GlobalScope 被用来启动一个协程时，它与作用域无关且是独立被启动的。
fun test_c3() {
    runBlocking {
        val request = launch {
            // 孵化了两个子作业 GlobalScope 启动
            GlobalScope.launch {
                println("11111111111 job1: I run in GlobalScope and execute independently!")
                delay(1000)
                println("2222222222 job1: I am not affected by cancellation of the request")
            }
            // 承袭了父协程的上下文
            launch {
                delay(100)
                println("3333333333 job2: I am a child of the request coroutine")
                delay(1000)
                println("44444444444 job2: I will not execute this line if my parent request is cancelled")
            }
        }
        delay(500)
        request.cancel() // 取消请求（request）的执行
        delay(1000) // 延迟一秒钟来看看发生了什么
        println("main: Who has survived request cancellation?")
    }
}

// 父协程的职责
// 一个父协程总是等待所有的子协程执行结束。
// 父协程并不显式的跟踪所有子协程的启动以及不必使用 Job.join 在最后的时候等待它们
fun test_c4() {
    runBlocking {
        // 启动一个协程来处理某种传入请求（request）
        val request = launch {
            repeat(3) { i ->
                // 启动少量的子作业
                launch {
                    delay((i + 1) * 200L) // 延迟 200 毫秒、400 毫秒、600 毫秒的时间
                    println("Coroutine $i is done")
                }
            }
            println("request: I'm done and I don't explicitly join my children that are still active")
        }
        request.join() // 等待请求的完成，包括其所有子协程
        println("Now processing of the request is complete")
    }
}

fun main_c2() = runBlocking<Unit> {
    //sampleStart
    val time = measureTimeMillis {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
//sampleEnd
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}


fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun test_c5() = runBlocking(CoroutineName("main")) {
    // sampleStart
    log("Started main coroutine")
    // run two background value computations
    val v1 = async(CoroutineName("v1coroutine")) {
        delay(500)
        log("Computing v1")
        252
    }
    val v2 = async(CoroutineName("v2coroutine")) {
        delay(1000)
        log("Computing v2")
        6
    }
    log("The answer for v1 / v2 = ${v1.await() / v2.await()}")
    // sampleEnd
}

val threadLocal = ThreadLocal<String?>() // declare thread-local variable

fun test_c6() = runBlocking<Unit> {
    //sampleStart
    threadLocal.set("main")
    println("Pre-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
    val job = launch(Dispatchers.Default + threadLocal.asContextElement(value = "launch")) {
        println("Launch start, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
        yield()
        println("After yield, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
    }
    job.join()
    println("Post-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
    //sampleEnd
}

fun main() {
    test_c6()
}