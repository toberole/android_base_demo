package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cat.anno_apt_apis.Zeus;
import com.cat.zeus.annotation.ZeusAPTBindView;
import com.cat.zeus.annotation.ZeusAPTOnClick;
import com.cat.zeus.annotation.ZeusBindView;
import com.xiaoge.org.R;

public class ZeusAPTBindViewOnClickProcessorActivity extends AppCompatActivity {
    @ZeusAPTBindView(R.id.btn_test0)
    Button btn_test0;

    @ZeusAPTBindView(R.id.btn_test1)
    Button btn_test1;

    @ZeusAPTBindView(R.id.btn_test2)
    Button btn_test2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zeus_aptbind_view_on_click_processor);
        Zeus.bind(this);
        btn_test2.setText("hahaha");
    }

    @ZeusAPTOnClick(R.id.btn_test0)
    void btn_test0() {
        btn_test0.setText("btn_test0 clicked");
        Toast.makeText(ZeusAPTBindViewOnClickProcessorActivity.this, "btn_test0", Toast.LENGTH_SHORT).show();
    }

    @ZeusAPTOnClick(R.id.btn_test1)
    void btn_test1() {
        btn_test1.setText("btn_test1 clicked");
        Toast.makeText(ZeusAPTBindViewOnClickProcessorActivity.this, "btn_test1", Toast.LENGTH_SHORT).show();
    }
}
