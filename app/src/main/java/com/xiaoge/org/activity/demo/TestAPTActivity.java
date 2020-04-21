package com.xiaoge.org.activity.demo;

import android.os.Bundle;
import android.widget.Button;

import com.cat.zeus.annotation.ZeusAPTBindView;
import com.xiaoge.org.R;

import androidx.appcompat.app.AppCompatActivity;

public class TestAPTActivity extends AppCompatActivity {

    @ZeusAPTBindView(R.id.btn_test1)
    Button btn_test1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_apt);
    }
}
