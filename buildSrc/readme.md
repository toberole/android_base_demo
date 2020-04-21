对Android打包过程 java文件会先转化为class文件，然后在转化为dex文件。
通过Gradle插件提供的Transform API，可以在编译成dex文件之前得到class文件。
得到class文件之后，便可以通过ASM对字节码进行修改，即可完成字节码插桩。

编译Android项目时，如果我们想拿到编译时产生的Class文件，并在生成Dex之前做一些处理，我们可以通过编写一个Transform来接收这些输入(编译产生的Class文件),并向已经产生的输入中添加一些东西。

我们可以通过Gradle插件来注册我们编写的Transform。注册后的Transform会被Gradle包装成一个Gradle Task，这个TransForm Task会在java compile Task执行完毕后运行。

编写Transform的API,可以通过引入下面这个依赖来使用:
compile 'com.android.tools.build:gradle:xxxx'  //版本应该在 2.x以上

Transform API 在编译之后，生成 dex 之前起作用

一般使用Transform会有下面两种场景:
1、对编译class文件做自定义的处理。
2、读取编译产生的class文件，做一些其他事情，但是不需要修改它。

TransformInvocation:
public interface TransformInvocation {

    Collection<TransformInput> getInputs(); // 输入作为 TransformInput 返回

    TransformOutputProvider getOutputProvider(); //TransformOutputProvider 可以用来创建输出内容

    boolean isIncremental();
}

public interface TransformInput {
    Collection<JarInput> getJarInputs();
    Collection<DirectoryInput> getDirectoryInputs();
}

public interface JarInput extends QualifiedContent {

    File getFile(); //jar文件

    Set<ContentType> getContentTypes(); // 是class还是resource

    Set<? super Scope> getScopes();  //属于Scope：
}

DirectoryInput与JarInput定义基本相同。

public interface TransformOutputProvider {
    //根据 name、ContentType、QualifiedContent.Scope返回对应的文件( jar / directory)
    File getContentLocation(String name, Set<QualifiedContent.ContentType> types, Set<? super QualifiedContent.Scope> scopes, Format format);
}

// 通过TransformInvocation来获取输入,同时也获得了输出的功能
// 获取jar的输入，然后遍历每一个jar做一些自定义的处理。
public void transform(TransformInvocation invocation) {
    for (TransformInput input : invocation.getInputs()) {
        input.getJarInputs().parallelStream().forEach(jarInput -> {
        File src = jarInput.getFile();
        JarFile jarFile = new JarFile(file);
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            // TODO 处理
        }
    }
}

// 获取输出路径
destDir = transformInvocation.outputProvider.getContentLocation(dirInput.name, dirInput.contentTypes, dirInput.scopes, Format.DIRECTORY)