package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Path;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.xiaoge.org.R;

/**
 * onSaveInstanceState和onRestoreInstanceState 只有在Activity异常终止的时候才会被系统回调
 */
public class LifeActivity_B extends AppCompatActivity {
    public static final String TAG = LifeActivity_B.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life__b);

        Log.i(TAG, "LifeActivity_B#onCreate");
    }

    @Override
    /**
     * 可见 但未获取焦点
     */
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "LifeActivity_B#onStart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.i(TAG, "LifeActivity_B#onRestoreInstanceState " + savedInstanceState.getString("test"));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);

        Log.i(TAG, "LifeActivity_B#onRestoreInstanceState ++++++");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("test", "test data");
        Log.i(TAG, "LifeActivity_B#onSaveInstanceState");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        Log.i(TAG, "LifeActivity_B#onSaveInstanceState ++++++");
    }

    @Override
    /**
     * Activity重建[activity 的stop生命周期方法被调用了] 之后回调
     */
    protected void onRestart() {
        super.onRestart();

        Log.i(TAG, "LifeActivity_B#onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG, "LifeActivity_B#onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, "LifeActivity_B#onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(TAG, "LifeActivity_B#onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "LifeActivity_B#onDestroy");
    }
}
