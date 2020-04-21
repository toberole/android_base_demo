package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.DebugUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.os.Debug;

import com.xiaoge.org.R;
import com.xiaoge.org.view_study.CircleView;

public class DemoViewActivity1 extends AppCompatActivity {
    @BindView(R.id.cv_test)
    CircleView cv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_view1);
        ButterKnife.bind(this);

        // Debug.startMethodTracing();
    }
}
