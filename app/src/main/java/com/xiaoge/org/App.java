package com.xiaoge.org;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import com.xiaoge.org.util.AppUtil;
import com.xiaoge.org.util.CrashHandler;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;

public class App extends Application implements Application.ActivityLifecycleCallbacks, Window.Callback {
    public static final String TAG = "app-xxx";
    public static int count = 0;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.getInstance().init(this);
        count++;

        Log.i(TAG, "App#onCreate " + getApplicationContext().getApplicationInfo().packageName +
                " pid: " + Process.myPid() +
                " uid: " + Process.myUid());

        init();

        // Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();

        CrashHandler.getInstance().init(this);
        //CrashReport.initCrashReport(getApplicationContext(), "ff76ba0cbd", true);

        // 反射做埋点
        registerActivityLifecycleCallbacks(this);
    }

    private void init() {
        File file = new File(Constant.TEMP_DIR);
        if (!file.exists()) file.mkdirs();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.i(TAG, "App#onActivityCreated: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.i(TAG, "App#onActivityStarted: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.i(TAG, "App#onActivityResumed: " + activity.getClass().getSimpleName());
        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
        // 当动态添加view的时候也可以做到点击代理的替换
        ViewTreeObserver viewTreeObserver = rootView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setViewProxy(rootView);
            }
        });

        try {
            setViewProxy(rootView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.i(TAG, "App#onActivityPaused: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.i(TAG, "App#onActivityStopped: " + activity.getClass().getSimpleName());
        // activity.getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener();
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.i(TAG, "App#onActivitySaveInstanceState: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.i(TAG, "App#onActivityDestroyed: " + activity.getClass().getSimpleName());
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

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return false;
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        return false;
    }

    @Nullable
    @Override
    public View onCreatePanelView(int featureId) {
        return null;
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        return false;
    }

    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        return false;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return false;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return false;
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams attrs) {

    }

    @Override
    public void onContentChanged() {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

    }

    @Override
    public void onAttachedToWindow() {

    }

    @Override
    public void onDetachedFromWindow() {

    }

    @Override
    public void onPanelClosed(int featureId, Menu menu) {

    }

    @Override
    public boolean onSearchRequested() {
        return false;
    }

    @Override
    public boolean onSearchRequested(SearchEvent searchEvent) {
        return false;
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return null;
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int type) {
        return null;
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onActionModeFinished(ActionMode mode) {

    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
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
