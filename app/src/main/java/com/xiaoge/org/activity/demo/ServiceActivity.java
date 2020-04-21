package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.xiaoge.org.R;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service2);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.startService)
    void start_service() {
        Intent intent = new Intent(ServiceActivity.this, ServiceTest.class);
        startService(intent);
    }

    @OnClick(R.id.stopService)
    void stop_Service() {
        Intent intent = new Intent(ServiceActivity.this, ServiceTest.class);
        stopService(intent);
    }

    @OnClick(R.id.bindService)
    void bind_Service() {
        Intent intent = new Intent(ServiceActivity.this, ServiceTest.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    @OnClick(R.id.unbindService)
    void unbind_Service() {

    }
}
