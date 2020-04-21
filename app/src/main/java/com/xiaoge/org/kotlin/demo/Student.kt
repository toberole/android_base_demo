package com.xiaoge.org.kotlin.demo

/**
 * constructor主构造函数
 * 在主构造函数中不能有任何的代码 必须放在init代码块中
 * 如果主构造函数没有任何注解或者可见性修饰符.
 * 如果构造函数有注解或可见性修饰符，constructor 关键字是必需的，并且修饰符在它前面
 */
// kotlin中的类定义同时也是构造函数
class Student public constructor(firstName: String) {
    public var firstName: String

    init {
        this.firstName = firstName
        println("hello Student")
    }
}

// open表示该类可以被继承 默认类都是不可以继承的
// 不写open关键字的 类 方法 属性 是不能被继承和重写的
open class Student1 {
    public var firstName: String = ""
    public var age: Int = 0

    constructor(firstName: String) {
        this.firstName = firstName
    }

    constructor(age: Int) {
        this.age = age
    }

    constructor(firstName: String = "", age: Int = 0) {
        this.firstName = firstName;
        this.age = age
    }

    open fun printName() {
        println("Student1: " + firstName)
    }
}

class Student2 : Student1 {
    constructor(name: String) {
        super.firstName = name
    }

    override fun printName() {
        println("Student2: " + firstName)
    }

    fun sys() {
        // 如果一个函数不返回任何有用的值，它的返回类型是 Unit。Unit 是一种只有一个值——Unit 的类型。这个 值可写可不写
        println("sys ...")
        return Unit// 可以不写
    }

    // 表达式函数
    fun cal(x: Int): Int = x * x

    // 可变参数 vararg
    fun printP(vararg ps: Int) {
        for (i in ps) {
            println(i)
        }
    }

    // 内部类
    inner class Inner {
        fun sys() {
            println(firstName)
        }
    }
}

// 接口 可以包含抽象方法的声明 也可以包含实现
interface Receiver {
    // 抽象方法
    fun sys()

    // 接口成员默认就是“open”的
    fun sys1() {
        println("Receiver sys1")
    }

    // 抽象属性
    var i: Int

    var ii: Int
        get() = ii
        set(value) {
            ii = value
        }

    val p: String
        get() = "test"
}

class ReceiverImpl(override var i: Int) : Receiver {
    override fun sys() {
        println(i)
    }
}

// 扩展 给ReceiverImpl添加一个add函数
fun ReceiverImpl.add() {
    // this扩展对象实例
    println("扩展函数： " + this.i)
}

// 可见性修饰符在 Kotlin 中有这四个可见性修饰符：
// private、 protected、 internal 和 public。
// 如果没有显式指定修饰符的话，默认可见性是 public
// private 意味着只在这个类内部（包含其所有成员）可见；
// protected—— 和 private一样 + 在子类中可见。
// internal —— 能见到类声明的本模块内的任何客户端都可见其 internal 成员；
// public —— 能见到类声明的任何客户端都可见其 public 成员。

// 数据类
//数据类必须满足以下要求：
//主构造函数需要至少有一个参数；
//主构造函数的所有参数需要标记为 val 或 var；
//数据类不能是抽象、开放、密封或者内部的；
data class User constructor(val name: String = "", val age: Int = 0) {
    // 在很多情况下，需要复制一个对象改变它的一些属性
    // 但其余部分保持不变。 copy() 函数就是为此而生成。
    //fun copy(name: String = this.name, age: Int = this.age) = User(name, age)
}

// 密封类
// 类名前面添加 sealed 修饰符。虽然密封类也可以 有子类，但是所有子类都必须在与密封类自身相同的文件中声明
sealed class Expr

data class Const(val number: Double) : Expr()
data class Sum(val e1: Expr, val e2: Expr) : Expr()
object NotANumber : Expr()

open class ParentClass(name: String) {
    lateinit var ss: String

    var name: String?
        get() {// get权限是和属性权限一致的
            return name
        }
        private set(value) {// set的权限可以属性的权限不一致
            name = value
        }

    init {
        this.name = name
        println("ParentClass#init")
    }

    open fun m1() {
        println("ParentClass#m1")
    }
}

interface ParentInterface {
    fun m1() {
        println("ParentInterface#m1")
    }
}

// 类定义时写构造函数称为主构造函数
// 类中定义的构造函数称为次级构造函数
// 子类无次级构造函数
class Child(name: String, age: Int) : ParentClass(name) {
    init {
        println("Child#init")
    }
}

// 有次级构造函数
class Child2 : ParentClass {
    // super 调用父类构造函数
    constructor(name: String) : super(name) {
        println("Child2#init 1")
    }

    constructor(name: String, age: Int) : super(name) {
        println("Child2#init 2")
    }
}

// 继承类中和实现类中的方法相同
class Child3(name: String) : ParentClass(name), ParentInterface {
    override fun m1() {
        // ParentClass.m1
        super<ParentClass>.m1()
        // ParentInterface.m1
        super<ParentInterface>.m1()
    }
}

abstract class AabstractParentClass {
    abstract fun abstractMethod()

    fun method() {
        println("AabstractParentClass#method")
    }
}


fun main() {
    var student = Student("hello")
    println(student.firstName)

    var stu2 = Student2("2 name")
    stu2.printName()
    stu2.sys()
    var i = stu2.cal(2)
    println(i)
    println("---------------")
    stu2.printP()
    println("---------------")
    stu2.printP(1, 2, 3, 4)

    // 内部类
    var innerClass = Student2("inner").Inner()
    innerClass.sys()

    var recv = ReceiverImpl(11)
    recv.sys()
    recv.i = 22
    recv.sys()
    // 调用扩展函数
    recv.add()
}