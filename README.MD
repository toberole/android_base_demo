javah -d "生成头文件的位置" "类全限定名称"
eg: javah -d ./../cpp/include xxxx


javap -s 查看签名信息

注意：
JNIEnv 不能在底层多线程间共享
jobject 指针不能在多个线程中共享

jni中引用的java对象的生命周期：
Java对象做为引用被传递到本地方法中，所有这些Java对象的引用都有一个共同的父类型jobject(相当于java中的 Object类是所有类的父类一样)。 这些对象引用都有其生命周期。在JNI中对Java对象的引用根据生命周期分为:全局引用，局部引用、弱全局引用
1、Local Reference 本地引用，
函数调用时传入jobject或者jni函数创建的jobejct，都是本地引用.
其特点就是一旦JNI层函数返回，jobject就被垃圾回收掉，所以需要注意其生命周期。可以强制调用DeleteLocalRef进行立即回收。
jstring pathStr = env->NewStringUTF(path)
env->DeleteLocalRef(pathStr);

2、Global Reference 全局引用 ，这种对象如不主动释放，它永远都不会被垃圾回收
创建： env->NewGlobalRef(obj);
释放： env->DeleteGlobalRef(obj)
若要在某个 Native 代码返回后，还希望能继续使用 JVM 提供的参数, 或者是过程中调用 JNI 函数的返回值（比如 g_mid）， 则将该对象设为 global reference，以后只能使用这个 global reference；若不是一个 jobject，则无需这么做。

3、Weak Global Reference 弱全局引用
一种特殊的 Global Reference ,在运行过程中可能被垃圾回收掉，所以使用时请务必注意其生命周期及随时可能被垃圾回收掉,比如内存不足时。
使用前可以利用JNIEnv的 IsSameObject 进行判定它是否被回收
env->IsSameObject(obj1,obj2)

在Android Studio 2.2 之后，工具中增加了 CMake 的支持，
你可以这么认为，在 Android Studio 2.2 之后你有2种选择来编译你写的
c/c++ 代码。一个是 ndk-build + Android.mk + Application.mk 组合，
另一个是 CMake + CMakeLists.txt 组合[目前IDE默认的方式]。这2个组合与Android代码和c/c++代码无关，
只是不同的构建脚本和构建命令。

# SO（CPU）的兼容
对于CPU来说，不同的架构并不意味着一定互不兼容，根据目前Android共支持七种不同类型的CPU架构，其兼容特点可总结如下：

armeabi设备只兼容armeabi；
armeabi-v7a设备兼容 armeabi-v7a、armeabi；
arm64-v8a设备兼容arm64-v8a、armeabi-v7a、armeabi；
X86设备兼容X86、armeabi；
X86_64设备兼容X86_64、X86、armeabi；
mips64设备兼容mips64、mips；
mips只兼容mips；

兼容总结：
armeabi的SO文件基本上可以说是万金油，它能运行在除了mips和mips64的设备上，但在非armeabi设备上运行性能还是有所损耗；
64位的CPU架构总能向下兼容其对应的32位指令集，如：x86_64兼容X86，arm64-v8a兼容armeabi-v7a，mips64兼容mips；
关于SO的兼容规律就介绍到此，下面谈谈适配工作。

# SO的适配
从目前移动端CPU市场的份额数据看，ARM架构几乎垄断，所以，除非你的用户很特殊，
否则几乎可以不考虑单独编译带入X86、X86_64、mips、mips64架构SO文件。除去这四个架构之后，
还要带入armeabi、armeabi-v7a、arm64-v8a这三个不同类型，这对于一个拥有大量SO文件的应用来说，
安装包的体积将会增大不少。

针对以上情况，可以应用的设备分布和市场情况再进行取舍斟酌，如果你的应用仍有不少armeabi类型的设备，可以考虑只保留armeabi目录下的SO文件（万金油特性）。
但是，尽管armeabi可以兼容多种平台，仍有些运算在armeabi-v7a、arm64-v8a去使用armeabi的SO文件时，性能会非常差强人意，
所以还是应该用其对应平台架构的SO文件进行运算。注意，这里并不是要带多一整套SO文件到不同的目录下，而是将性能差异比较明显的
某个armeabi-v7a、arm64-v8a平台下的SO文件放到armeabi目录，然后通过代码判断设备的CPU类型，再加载其对应架构的SO文件，很多大厂的应用便是这么做的。如微信的lib下虽然只有armeabi一个目录，但目录内的文件仍放着v5、v7a架构的SO文件，用于处理兼容带来的某些性能运算问题。

