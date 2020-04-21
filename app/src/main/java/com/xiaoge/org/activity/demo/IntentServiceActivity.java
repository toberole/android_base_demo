package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.xiaoge.org.R;
import com.xiaoge.org.service.IntentService_Test;

public class IntentServiceActivity extends AppCompatActivity {
    @BindView(R.id.start_service)
    Button start_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
        ButterKnife.bind(this);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });

        // start_service.setOnClickListener(null);

        // registerReceiver()
    }

    @OnClick(R.id.start_service)
    void start_service() {
        Intent intent = new Intent(IntentServiceActivity.this, IntentService_Test.class);
        startService(intent);
    }
}
