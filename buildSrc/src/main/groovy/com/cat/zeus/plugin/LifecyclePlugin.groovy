package com.cat.zeus.plugin

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import org.gradle.api.Plugin
import org.gradle.api.Project

class LifecyclePlugin extends Transform implements Plugin<Project> {
    @Override
    void apply(Project project) {
        System.out.println("************** " + "apply")
        def android = project.extensions.getByType(AppExtension)
        // 注册Transform插件
        android.registerTransform(this)
    }

    @Override
    /**
     * 指定自定义的Transform的名字
     * 这个 name 并不是最终的名字 ，在 TransformManager 中会对名字再处理：
     */
    String getName() {
        System.out.println("************** " + "getName")
        return LifecyclePlugin.class.getSimpleName()
    }

    @Override
    /**
     * 指定自定义的Transform处理的输入类型
     */
    Set<QualifiedContent.ContentType> getInputTypes() {
        System.out.println("************** " + "getInputTypes")
        // CLASSES 表示要处理编译后的字节码 可能是 jar 包也可能是目录
        // RESOURCES 表示处理标准的 java 资源
        return TransformManager.CONTENT_CLASS
    }

    @Override
    /**
     * 指定输入文件的范围
     *
     *         PROJECT(0x01), //只是当前工程的代码
     *         PROJECT_LOCAL_DEPS(0x02), // 工程的本地jar
     *         SUB_PROJECTS(0x04),  // 只包含子工工程
     *         SUB_PROJECTS_LOCAL_DEPS(0x08),
     *         EXTERNAL_LIBRARIES(0x10),
     *         TESTED_CODE(0x20),
     *         PROVIDED_ONLY(0x40);
     *
     *         PROJECT	只处理当前项目
     *         SUB_PROJECTS	只处理子项目
     *         PROJECT_LOCAL_DEPS	只处理当前项目的本地依赖,例如jar, aar
     *         EXTERNAL_LIBRARIES	只处理外部的依赖库
     *         PROVIDED_ONLY	只处理本地或远程以provided形式引入的依赖库
     *         TESTED_CODE	测试代码
     *
     * 如果一个Transform不想处理任何输入，只是想查看输入的内容,
     * 那么只需在getScopes()返回一个空集合，在getReferencedScopes()返回想要接收的范围。
     */
    Set<? super QualifiedContent.Scope> getScopes() {
        System.out.println("************** " + "getScopes")
        // ImmutableSet.of()
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {// 是否支持增量编译
        System.out.println("************** " + "isIncremental")
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        System.out.println("************** " + "transform")
    }
}