package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.ContentProvider;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.xiaoge.org.R;


public class LifeActivity extends AppCompatActivity {
    public static final String TAG = LifeActivity.class.getSimpleName();

    @Override
    /**
     * 可见 但未获取焦点
     */
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "LifeActivity#onStart");
    }

    @Override
    /**
     * Activity重建[activity 的stop生命周期方法被调用了] 之后回调
     */
    protected void onRestart() {
        super.onRestart();

        Log.i(TAG, "LifeActivity#onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG, "LifeActivity#onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, "LifeActivity#onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(TAG, "LifeActivity#onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "LifeActivity#onDestroy");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.i(TAG, "LifeActivity#onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "LifeActivity#onRestoreInstanceState");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "LifeActivity#onNewIntent");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        Log.i(TAG, "LifeActivity#onCreate");

        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_click)
    void tv_click() {
        Intent intent = new Intent(LifeActivity.this, LifeActivity_B.class);
        LifeActivity.this.startActivity(intent);
    }

    @OnClick(R.id.btn_click)
    void btn_click() {
        Intent intent = new Intent(LifeActivity.this, MultiProcessActivity.class);
        LifeActivity.this.startActivity(intent);
    }

    @OnClick(R.id.btn_click2)
    void btn_click2() {
        Intent intent = new Intent(LifeActivity.this, MultiProcessActivity2.class);
        LifeActivity.this.startActivity(intent);
    }
}
