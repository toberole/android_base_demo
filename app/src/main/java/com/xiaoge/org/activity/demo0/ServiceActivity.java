package com.xiaoge.org.activity.demo0;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.xiaoge.org.R;
import com.xiaoge.org.service.MyService;

public class ServiceActivity extends AppCompatActivity {
    private MyService.LocalBinder localBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_startService)
    void btn_startService() {
        Intent intent = new Intent(ServiceActivity.this, MyService.class);
        ServiceActivity.this.startService(intent);
    }

    @OnClick(R.id.btn_bind_serivce)
    void btn_bind_serivce() {
        Intent intent = new Intent(ServiceActivity.this, MyService.class);
        ServiceActivity.this.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @OnClick(R.id.btn_unbindService)
    void btn_unbindService() {
        ServiceActivity.this.unbindService(connection);
    }

    @OnClick(R.id.btn_callService)
    void btn_callService() {
        if (localBinder != null) {
            localBinder.hello();
        }
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("test_xxxx", "bind_connection onServiceConnected");

            localBinder = (MyService.LocalBinder) service;
            localBinder.hello();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("test_xxxx", "bind_connection onServiceDisconnected");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("test_xxxx", "ServiceActivity#onDestroy");
    }
}
