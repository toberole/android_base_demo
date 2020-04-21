package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoge.org.R;

public class AsyncLayoutInflaterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_layout_inflater);

        // AsyncLayoutInflater
        new AsyncLayoutInflater(this).inflate(R.layout.activity_main,null, new AsyncLayoutInflater.OnInflateFinishedListener(){
            @Override
            public void onInflateFinished(View view, int resid, ViewGroup parent) {
                setContentView(view);
            }
        });
    }
}
