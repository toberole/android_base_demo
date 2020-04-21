package com.xiaoge.org.kotlin.demo

import kotlin.properties.Delegates

/**
 * 枚举 object 单例 static
 */

// 枚举
enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

/*
object是Kotlin中的一个重要的关键字，object主要有以下三种使用场景：
对象声明（Object Declaration）
伴生对象（Companion Object）
对象表达式（Object Expression）

对比object declaration、Companion object以及object expression的初始化时机：

a. object declaration：当第一次访问它时才初始化，是一种懒初始化

b. Companion object：当它对应的类被加载后，它才初始化，类似Java中的静态代码块

c. object expression：一旦它被执行，立马初始化

*/

// 实现单例

object Singleton {
    fun closeFile() {
        println("closeFile")
    }
}


// 定义在内部类
class ObjectOuter {
    object Inner {
        fun method() {
            println("I'm in inner class")
        }
    }
}

// 伴生对象
// 在Kotlin中是没有static关键字的，也就是意味着没有了静态方法和静态成员。
// 那么在kotlin中如果要想表示这种概念，取而代之的是包级别函数（package-level function）
// 和我们这里提到的伴生对象。
// 语法形式
//class A{
//    companion object 伴生对象名(可以省略){
//        //define method and field here
//    }
//}
class Object_Companio {
    companion object MyObject {
        var a = 20
        @JvmStatic
        fun method() {
            println("I'm in companion object method")
        }

        // 加不加JvmStatic注解 效果一样
        // 可以通过反编译看源代码
        fun method1() {
            println("I'm in companion object method1")
        }
    }
}

// 对象表达式（Object Expression）
// 语法形式：object [ : 接口1,接口2,类型1, 类型2]{}
// 实现一个接口或者类
interface AA {
    fun a()
}

interface BB {
    fun b()
}

// 匿名对象
// 匿名对象只有定义成局部变量和private成员变量时，才能体现它的真实类型。
// 如果将匿名对象作为public函数的返回值或者是public属性时，只能将它看做是它的父类，
// 不指定任何类型时就当做Any，在匿名对象中添加的属性和方法是不能够被访问的。
class MyTest {

    private val foo = object {
        fun method() {
            println("private")
        }
    }

    val foo2 = object {
        fun method() {
            println("public")
        }
    }

    fun m() = object {
        fun method() {
            println("method")
        }
    }

    fun invoke() {

        val local = object {
            fun method() {
                println("local")
            }
        }

        local.method()  //编译通过
        foo.method()    //编译通过
        // foo2是public的
        // foo2.method()   //编译通不过
        // m()是public的
        // m().method()    //编译通不过
    }
}

// 类委托
// 委托模式是实现继承的一个很好的替代方式， 而 Kotlin 可以零样板代码地原生支持它。
interface Base {
    fun printInfo()
}

class Base_Impl(x: Int) : Base {
    override fun printInfo() {
        println("Base_Impl::printInfo")
    }
}

// Derived 的超类型列表中的 by-子句表示 b 将会在 Derived 中内部存储。
// 并且编译器将生成转发给 b 的所有Base的方法。相当于java里面的动态代理的实现原理
class Derived(b: Base) : Base by b

// 委托属性
/*
    延迟属性（lazy properties）: 其值只在首次访问时计算，
    可观察属性（observable properties）: 监听器会收到有关此属性变更的通知，
    把多个属性储存在一个映射（map）中，而不是每个存在单独的字段中。
*/
// 延迟属性 Lazy
// lazy() 是接受一个 lambda 并返回一个 Lazy <T> 实例的函数，返回的实例可以作为实现延迟属性的委托： 第一次调用 get() 会执行已传递给 lazy() 的 lamda 表达式并记录结果， 后续调用 get() 只是返回记录的结果。
val lazyValue: String by lazy {
    println("hello computed")
    "hello"
}

// 可观察属性 Observable
// Delegates.observable() 接受两个参数：初始值和修改时处理程序（handler）。
// 每当给属性赋值时会调用该处理程序（在赋值后执行）。
// 它有三个 参数：被赋值的属性、旧值和新值：
// 如果想能够截获一个赋值并“否决”它，就使用 vetoable() 取代 observable()。 在属性被赋新值生效之前会调用传递给 vetoable 的处理程序。
class P {
    var name: String by Delegates.observable("<no name>") { prop, old, new ->
        println("$prop - $old - $new")
    }
}

// 把属性储存在映射中 Map只读map
class P1(val map: Map<String, Any?/*? 代表可空*/>) {
    val name: String by map
    val age: Int by map
}

// MutableMap可读可写
class P2(var map: MutableMap<String, Any?>) {
    var name: String by map
    var age: Int by map
}


/*


属性委托要求
对于一个只读属性（即 val 声明的），委托必须提供一个名为 getValue 的函数，该函数接受以下参数：
thisRef —— 必须与 属性所有者 类型（对于扩展属性——指被扩展的类型）相同或者是它的超类型，
property —— 必须是类型 KProperty<*> 或其超类型，
这个函数必须返回与属性相同的类型（或其子类型）。
对于一个可变属性（即 var 声明的），委托必须额外提供一个名为 setValue 的函数，该函数接受以下参数：
thisRef —— 同 getValue()，
property —— 同 getValue()，
new value —— 必须和属性同类型或者是它的超类型。
getValue() 或/和 setValue() 函数可以通过委托类的成员函数提供或者由扩展函数提供。 当你需要委托属性到原本未提供的这些函数的对象时后者会更便利。 两函数都需要用 operator 关键字来进行标记。

 */


fun main() {
    // 在kotlin中没有静态方法
    Singleton.closeFile()
    ObjectOuter.Inner.method()

    // 使用伴生对象
    // 方式一
    println(Object_Companio.a)
    Object_Companio.method()
    // 方式二 类似java static成员属性和方法的使用
    println(Object_Companio.a)
    Object_Companio.method()
    Object_Companio.method1()

    // onject 对象表达式
    var aa = object : AA {
        // 可以理解为匿名继承
        // 与此同时也声明它的一个对象
        override fun a() {
            println("object : AA")
        }
    }
    aa.a()
    var cc = object : AA, BB {
        override fun a() {
            println("AA:a")
        }

        override fun b() {
            println("BB:b")
        }
    }
    cc.a()
    cc.b()

    // 类委托
    var b = Base_Impl(10)
    Derived(b).printInfo()

    // 延迟属性 Lazy
    println(lazyValue)
    println(lazyValue)

    // 可观察属性 Observable
    var p = P()
    p.name = "hello"
    p.name = "world"

    // 把属性储存在映射中
    var p1 = P1(mapOf(
            "name" to "john doe",
            "age" to 25
    ))
    println(p1.name)
    println(p1.age)
}