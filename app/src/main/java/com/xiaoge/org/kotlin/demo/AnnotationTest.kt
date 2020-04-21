package com.xiaoge.org.kotlin.demo

/**
 * JvmStatic、JvmField 用于与java互操作
 */
class AnnotationTest {
    companion object {
        @JvmStatic
        var name: String = "hello"

        @JvmField
        var age: Int = 11
    }
}