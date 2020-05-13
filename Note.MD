一般情况下，同一个应用程序的Activity组件都是运行在同一个进程中，但是如果Activity配置了android:process这个属性，
那么，它就会运行在自己的进程中。
如果android:process属性的值以":"开头，则表示这个进程是私有的；
如果android:process属性的值以小写字母开头，则表示这是一个全局进程，允许其它应用程序组件也在这个进程中运行。

AIDL
aidl通信相当于是网络中RPC通信[客户端调用服务提供的方法]，aidl文件相当与是协议文件，ide会根据该
aidl文件生成对应的通信代码。
1、在app/src/main 下面创建一个aidl文件夹，该文件夹里面放置
aidl文件
2、构建工程，ide会自动的根据aidl文件生成对应的java文件

注意：客户端通过aidl的方式调用服务端的方法是阻塞式的，且服务端的方法是在线程池中
运行的[binder ipc底层的框架实现的],所以服务端的方法没有必要在开启线程去执行任务。

跨进程通信
1、共享文件
2、socket
3、aidl
4、ContentProvider

单个对象SqliteDatabase内部会对数据库的操作作同步处理

FileProvider:
    root-path 对应DEVICE_ROOT,也就是File DEVICE_ROOT = new File("/")，即根目录，一般不需要配置。
    files-path对应 content.getFileDir() 获取到的目录。
    cache-path对应 content.getCacheDir() 获取到的目录。
    external-path对应 Environment.getExternalStorageDirectory() 指向的目录。
    external-files-path对应 ContextCompat.getExternalFilesDirs() 获取到的目录。
    external-cache-path对应 ContextCompat.getExternalCacheDirs() 获取到的目录。
    
当加载资源文件夹下面的图片时，图片占用的内存为：
占用的内存 = width * height * nTargetDensity/inDensity * nTargetDensity/inDensity * 一个像素所占的内存
nTargetDensity是屏幕的密度，inDensity是图片所在资源文件夹所对应的密度
计算公式：
对资源文件图片：width * height * nTargetDensity/inDensity * nTargetDensity/inDensity * 一个像素所占的内存；
别的资源图片：width * height * 一个像素所占的内存；

Bitmap的压缩
Bitmap.compress()，质量压缩，不会对内存产生印象；
BitmapFactory.Options.inSampleSize，内存压缩；
inSampleSize的比对获取；
