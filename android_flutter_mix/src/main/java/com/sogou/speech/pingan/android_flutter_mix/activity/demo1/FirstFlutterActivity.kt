package com.sogou.speech.pingan.android_flutter_mix.activity.demo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.zw.android_flutter_mix.R
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor
import kotlinx.android.synthetic.main.activity_first_flutter.*

/*
在Android 套壳原生页面FirstFlutterActivity引入Flutter页面。
并在Flutter页面定义如下内容：添加一个textView用来显示其他页面
传过来的内容添加一个button用来打开下个原生页面添加一个button用
来返回到上个原生页面
思路：
新建FlutterView -> 在xml中拖一个FrameLayout -> 将FlutterView添加到FrameLayout中 -> 创建FlutterEngine，
并初始化引擎指向一个Flutter页面的路由-> FlutterView使用FlutterEngine加载内容。
上面的介绍和WebView的加载方式如出一辙。

FlutterEngine：Flutter负责在Android端执行Dart代码的引擎，将Flutter编写的UI代码渲染到FlutterView中。
 */
class FirstFlutterActivity : AppCompatActivity() {
    companion object {
        val TAG = FirstFlutterActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_flutter)


        // 创建FlutterEngine，并渲染路由名称为route1的Flutter页面，
        // 路由可以携带一些数据(字符串：message)
        var flutterEngine = FlutterEngine(this@FirstFlutterActivity)

        var str = "route hello";
        flutterEngine.navigationChannel.setInitialRoute(str)
        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
        // 渲染Flutter页面
        flutterView.attachToFlutterEngine(flutterEngine)

        Log.i(TAG, "FirstFlutterActivity#onCreate")
    }
}
