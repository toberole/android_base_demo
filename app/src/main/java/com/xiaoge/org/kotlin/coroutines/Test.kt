package com.xiaoge.org.kotlin.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import java.lang.System.currentTimeMillis

fun test1() {
    GlobalScope.launch {
        // 在后台启动一个新的协程并继续
        delay(1000L) // 非阻塞的等待 1 秒钟（默认时间单位是毫秒）
        println("World!") // 在延迟后打印输出
    }

    println("Hello,") // 协程已在等待时主线程还在继续
    Thread.sleep(2000L) // 阻塞主线程 2 秒钟来保证 JVM 存活
}

fun test2() {
    GlobalScope.launch {
        // 在后台启动一个新的协程并继续
        delay(1000L) // 非阻塞的等待 1 秒钟（默认时间单位是毫秒）
        println("World!") // 在延迟后打印输出
    }

    println("Hello,") // 协程已在等待时主线程还在继续
    runBlocking {
        kotlinx.coroutines.delay(2000)
    }
}

fun test3() {
    runBlocking {
        GlobalScope.launch {
            // 在后台启动一个新的协程并继续
            delay(1000L) // 非阻塞的等待 1 秒钟（默认时间单位是毫秒）
            println("World!") // 在延迟后打印输出
        }
        println("Hello,") // 协程已在等待时主线程还在继续
        kotlinx.coroutines.delay(2000)
    }
}

fun test4() {
    runBlocking {
        val job = GlobalScope.launch {
            kotlinx.coroutines.delay(1000)
            println("==========")
        }

        println("main ---------")

        job.join()

        println("main -----0000----")
    }
}

fun main1() {
    test3()
}

// 使用 runBlocking 协程构建器将 main 函数转换为协程
// 外部协程直到在其作用域中启动的所有协程都执行完毕后才会结束。
fun main2() = runBlocking<Unit> {
    // this: CoroutineScope
    launch {
        // 在 runBlocking 作用域中启动一个新协程
        delay(1000L)
        println("World!")
    }
    println("Hello,")
}

// runBlocking 与 coroutineScope 的主要区别
// 在于后者在等待所有子协程执行完毕时不会阻塞当前线程。
fun main3() = runBlocking<Unit> {
    // this: CoroutineScope
    launch {
        test_c()
    }
    println("000000000000000000")

    coroutineScope {
        // 创建一个协程作用域
        launch {
            delay(2500L)
            println("coroutineScope Task from nested launch")
        }

        delay(100L)
        println("coroutineScope Task from coroutine scope") // 这一行会在内嵌 launch 之前输出
    }

    println("111111111111111111111")

    println("Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
}

private suspend fun test_c() {
    delay(200L)
    println("Task from runBlocking")
}

// 取消协程的执行
fun test5() {
    runBlocking {
        var job = launch {
            repeat(1000) { i ->
                println("job i = $i")
                kotlinx.coroutines.delay(500)
            }
        }

        kotlinx.coroutines.delay(1000)
        println("waiting ......")
//        job.cancel()
//        job.join()
        job.cancelAndJoin()
        println("... end ...")
    }
}

// 协程的取消是 协作 的。一段协程代码必须协作才能被取消。
// 所有 kotlinx.coroutines 中的挂起函数都是 可被取消的 。
// 它们检查协程的取消， 并在取消时抛出 CancellationException。
// 然而，如果协程正在执行计算任务，并且没有检查取消的话，那么它是不能被取消的
fun test6() {
    runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) { // 一个执行计算的循环，只是为了占用 CPU
                // 每秒打印消息两次
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(300L) // 等待一段时间
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // 取消一个作业并且等待它结束
        println("main: Now I can quit.")
    }
}

// 使计算代码可取消
// 我们有两种方法来使执行计算的代码可以被取消。
// 第一种方法是定期调用挂起函数来检查取消。对于这种目的 yield 是一个好的选择。
// 另一种方法是显式的检查取消状态。
fun test7() {
    runBlocking {
        val startTime = currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            // isActive 是一个可以被使用在 CoroutineScope 中的扩展属性。
            while (isActive) { // cancellable computation loop
                // print a message twice a second
                if (currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }

        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancels the job and waits for its completion
        println("main: Now I can quit.")
    }
}

// 在 finally 中释放资源
fun test8() {
    runBlocking {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("job: I'm sleeping $i ...")
                    delay(500L)
                }
            } finally {
                println("job: I'm running finally")
            }
        }

        delay(1300L) // 延迟一段时间
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // 取消该作业并且等待它结束
        println("main: Now I can quit.")
    }
}

// 运行不能取消的代码块
// 需要挂起一个被取消的协程，可以将相应的代码包装在 withContext(NonCancellable) {……} 中
// 并使用 withContext 函数以及 NonCancellable 上下文
fun test9() {
    runBlocking {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("job: I'm sleeping $i ...")
                    delay(500L)
                }
            } finally {
                withContext(NonCancellable) {
                    println("job: I'm running finally")
                    delay(1000L)
                    println("job: And I've just delayed for 1 sec because I'm non-cancellable")
                }
            }
        }
        delay(1300L) // 延迟一段时间
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // 取消该作业并等待它结束
        println("main: Now I can quit.")
    }
}

fun test10() {
    runBlocking {
        try {
            withTimeout(1300L) {
                repeat(1000) { i ->
                    println("I'm sleeping $i ...")
                    delay(500L)
                }
            }
        } catch (e: TimeoutCancellationException) {
            println("------------")
            e.printStackTrace()
            println("++++++++++++")
        } finally {
            println("... finally ...")
        }
    }
}


// 通道
// 一个 Channel 是一个和 BlockingQueue 非常相似的概念。
// 其中一个不同是它代替了阻塞的 put 操作并提供了
fun test11() {
    runBlocking {
        val chanal = Channel<Int>()
        launch {
            for (x in 1..5) {
                chanal.send(x)
                kotlinx.coroutines.delay(100)
            }
        }

        repeat(5) {
            println(chanal.receive())
        }
    }
}

// 关闭与迭代通道
fun test12() {
    runBlocking {
        val channel = Channel<Int>()
        launch {
            for (x in 1..5) channel.send(x * x)
            channel.close() // 我们结束发送
        }
        // 这里我们使用 `for` 循环来打印所有被接收到的元素（直到通道被关闭）
        for (y in channel) println(y)
        println("Done!")
    }
}

// 构建通道生产者
// 名为 produce 的便捷的协程构建器，可以很容易的在生产者端正确工作
// 并且我们使用扩展函数 consumeEach 在消费者端替代 for 循环
fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
    for (x in 1..5) send(x * x)
}

fun test13() {
    runBlocking {
        val squares = produceSquares()
        squares.consumeEach { println(it) }
        println("Done!")
    }
}

fun main() {
    test13()
}