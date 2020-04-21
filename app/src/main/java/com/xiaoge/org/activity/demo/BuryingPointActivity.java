package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.xiaoge.org.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * AppClick全埋点
 * 动态代理方案
 * <p>
 * 存在问题：
 * 1、使用反射，效率比较低，对于性能会有影响，可能也会有兼容性问题
 * 2、Application.ActivityLifecycleCallbacks 需要 API 14+
 * 3、View.hasOnClickListeneers 需要 API 15+
 * 4、removeOnGlobalLayoutListener 需要 API 16+
 * 5、游离于Activity 之上的View的点击比如Dialog，PopupWindow无法被监视
 * <p>
 * 当然我们可以代理Window.Callback 和上面的原理相同。不过问题依然存在。
 * 代理View.AccessibilityDelegate效果也是差不多的，问题依然存在。
 */
public class BuryingPointActivity extends AppCompatActivity {
    public static final String TAG = BuryingPointActivity.class.getSimpleName();

    private AppCompatButton btn_appCompatButton;
    private ViewGroup rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burying_point);
        btn_appCompatButton = findViewById(R.id.btn_AppCompatButton);
        btn_appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "btn_appCompatButton clicked");
            }
        });

        // runOnUiThread();

        toast_test();

        Fragment fragment = null;
    }

    /**
     * 异步线程淡出toast
     */
    private void toast_test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(BuryingPointActivity.this.getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();

//        rootView = findViewById(android.R.id.content);
//        // 当动态添加view的时候也可以做到点击代理的替换
//        ViewTreeObserver viewTreeObserver = rootView.getViewTreeObserver();
//        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                setViewProxy(rootView);
//            }
//        });
//
//        try {
//            setViewProxy(rootView);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void setViewProxy(ViewGroup rootView) {
        if (null != rootView) {
            int count = rootView.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = rootView.getChildAt(i);
                if (v instanceof ViewGroup) {
                    setViewProxy((ViewGroup) v);
                } else {
                    hook(v);
                }
            }
        }
    }

    private void hook(View view) {
        try {
            Method getListenerInfo = View.class.getDeclaredMethod("getListenerInfo");
            getListenerInfo.setAccessible(true);
            Object listenereInfo = getListenerInfo.invoke(view);
            Class<?> listenerInfoClazz = Class.forName("android.view.View$ListenerInfo");
            Field mOnClickListener = listenerInfoClazz.getDeclaredField("mOnClickListener");
            mOnClickListener.setAccessible(true);
            View.OnClickListener originOnClickListener = (View.OnClickListener) mOnClickListener.get(listenereInfo);
            if (originOnClickListener == null || originOnClickListener instanceof OnClickListenererProxy) {
                return;
            } else {
                OnClickListenererProxy proxyOnClick = new OnClickListenererProxy(originOnClickListener);
                mOnClickListener.set(listenereInfo, proxyOnClick);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class OnClickListenererProxy implements View.OnClickListener {
        private View.OnClickListener onClickListener;

        public OnClickListenererProxy(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        @Override
        public void onClick(View v) {
            preClicked();
            onClickListener.onClick(v);
            afterClicked();
        }

        private void afterClicked() {
            Log.i(TAG, "OnClickListenererProxy#afterClicked");
        }

        private void preClicked() {
            Log.i(TAG, "OnClickListenererProxy#preClicked");
        }
    }
}
