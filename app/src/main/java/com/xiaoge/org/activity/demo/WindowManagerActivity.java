package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoge.org.R;

public class WindowManagerActivity extends AppCompatActivity {
    public static final String TAG = WindowManagerActivity.class.getSimpleName();

    @BindView(R.id.ll_container)
    LinearLayout ll_container;
    private WindowManager windowManager;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_manager);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(WindowManagerActivity.this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 10);
            }
        }
    }

    /**
     * Windowmanager 添加一个view
     * 可以模拟Toast
     */
    @OnClick(R.id.btn_wm)
    void btn_wm() {
        Log.i(TAG, "btn_wm");

        WindowManager windowManager = getWindowManager();
        View view = View.inflate(WindowManagerActivity.this, R.layout.wm_layout, null);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0, 0, PixelFormat.OPAQUE
        );
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        params.gravity = Gravity.CENTER;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
        }

        windowManager.addView(view, params);
    }

    /**
     * Windowmanager 添加一个view
     * 可以模拟Toast
     */
    @OnClick(R.id.btn_toast)
    void btn_toast() {
        Log.i(TAG, "btn_wm");

        windowManager = getWindowManager();
        view = View.inflate(WindowManagerActivity.this, R.layout.wm_layout, null);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0, 0, PixelFormat.OPAQUE
        );
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        params.gravity = Gravity.CENTER;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
//            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_STARTING;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
        }

        windowManager.addView(view, params);

        ll_container.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (view != null && windowManager != null) {
                    windowManager.removeView(view);
                }
            }
        }, 1500);
    }

    @OnClick(R.id.btn_toast_50)
    void btn_toast_50() {
        for (int i = 0; i < 100; i++) {
            Toast.makeText(WindowManagerActivity.this, "i = " + i, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_toast_aysn)
    void btn_toast_aysn() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "非UI线程弹Toast", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @OnClick(R.id.btn_NonUiThread)
    void btn_NonUiThread() {
        new NonUiThread().start();
    }

    class NonUiThread extends Thread {
        @Override
        public void run() {
            Looper.prepare();
            TextView tx = new TextView(WindowManagerActivity.this);
            tx.setTextColor(Color.RED);
            tx.setBackgroundColor(Color.WHITE);
            tx.setText("non-UiThread update textview");

            WindowManager windowManager = WindowManagerActivity.this.getWindowManager();
            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    200, 200, 200, 200, WindowManager.LayoutParams.FIRST_SUB_WINDOW,
                    WindowManager.LayoutParams.TYPE_TOAST, PixelFormat.OPAQUE);
            windowManager.addView(tx, params);
            Looper.loop();
        }
    }
}
