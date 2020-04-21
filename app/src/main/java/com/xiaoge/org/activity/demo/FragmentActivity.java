package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.xiaoge.org.R;

public class FragmentActivity extends AppCompatActivity {

    private LinearLayout ll_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        ll_container = findViewById(R.id.ll_container);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
