package com.cat.zeus.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 在系统自动生成 BuildConfig.java 文件后（也就是系统内置任务 generateDebugBuildConfig 之后新建任务执行），自动生成我们自定义的 java 代码文件
 *
 * 利用 Javassist，在系统自动生成BuildConfig.java文件后，自动生成我们的java文件
 */
class CreateJavaPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        System.out.println("***** CreateJavaPlugin *****")
        def android = project.extensions.getByType(AppExtension)
        // 注册一个Transform
        //android.registerTransform(new MyTransform(project))
        // 创建一个Extension
        //project.extensions.create("testCreateJavaConfig", CreateJavaExtension)
    }
}

class CreateJavaExtension {
    def str = "动态生成Java类的字符串"
}