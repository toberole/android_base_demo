package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.cat.zeus.annotation.InjectView;
import com.xiaoge.org.R;

public class TestInjectViewActivity extends AppCompatActivity {
    @InjectView(R.id.label)
    TextView mLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_inject_view);
    }
}
