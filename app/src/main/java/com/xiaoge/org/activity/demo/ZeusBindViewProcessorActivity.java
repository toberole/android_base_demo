package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cat.anno_apt_apis.Zeus;
import com.cat.zeus.annotation.ZeusAPTBindView;
import com.xiaoge.org.R;

public class ZeusBindViewProcessorActivity extends AppCompatActivity {

    @ZeusAPTBindView(R.id.btn_test)
    Button btn_test;

    @ZeusAPTBindView(R.id.tv_test)
    TextView tv_test;

    @ZeusAPTBindView(R.id.et_test)
    EditText et_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zeus_bind_view_processor);
        Zeus.bind(this);

        initData();
    }

    private void initData() {
        tv_test.setText("bind after");
        et_test.setText("hello et_test");
        btn_test.setText("hello button");
    }
}
