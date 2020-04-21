package com.xiaoge.org.activity.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.PersistableBundle;

import com.xiaoge.org.R;
import com.xiaoge.org.util.LogUtil;

/**
 * onCreate -> onStart -> onResume -> onPause -> onStop -> onSaveInstanceState
 *
 */
public class ConstraintlayoutActivity extends AppCompatActivity {
    public static final String TAG = ConstraintlayoutActivity.class.getSimpleName() + "-xxx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constraintlayout1);
        String str = null;
        if (null != savedInstanceState) {
            str = savedInstanceState.getString("xxx-test");
        }
        LogUtil.i(TAG, "onCreate str: " + str);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.i(TAG, "onStart");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.i(TAG, "onSaveInstanceState 1 ...");
        outState.putString("xxx-test", "hello onSaveInstanceState");
    }

    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        onSaveInstanceState(outState);
        LogUtil.i(TAG, "onSaveInstanceState 2 ...");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.i(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG, "onDestroy");
    }

    private void test() {
        Looper.prepare();
        Looper.loop();

        Handler handler = null;
        handler.postDelayed(null, 100);
        HandlerThread handlerThread = new HandlerThread("test");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }
}
