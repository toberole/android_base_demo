package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.cat.zeus.annotation.TestAnno;
import com.xiaoge.org.R;

public class TestAPT1Activity extends AppCompatActivity {
    @TestAnno(R.id.btn_xxxx)
    Button btn_xxxx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_apt1);
    }
}
