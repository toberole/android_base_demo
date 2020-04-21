package com.xiaoge.org.activity.demo0;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;

import com.xiaoge.org.R;

public class TestServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_service);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_test)
    void btn_test() {
        Intent intent = new Intent(TestServiceActivity.this, ServiceActivity.class);
        TestServiceActivity.this.startActivity(intent);
    }
}
