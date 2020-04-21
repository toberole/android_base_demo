package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Button;

import com.xiaoge.org.R;
import com.xiaoge.org.bean.IUserManager;
import com.xiaoge.org.bean.User;
import com.xiaoge.org.service.TestService;

public class TestServiceActivity extends AppCompatActivity {
    public static final String TAG = TestServiceActivity.class.getSimpleName();

    @BindView(R.id.btn_start)
    Button btn_start;

    @BindView(R.id.btn_bind)
    Button btn_bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_service2);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_start)
    void btn_start() {
        Intent intent = new Intent(TestServiceActivity.this, TestService.class);
        TestServiceActivity.this.startService(intent);
    }

    @OnClick(R.id.btn_bind)
    void btn_bind() {
        Intent intent = new Intent(TestServiceActivity.this, TestService.class);
        TestServiceActivity.this.bindService(intent, conn, BIND_AUTO_CREATE);
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "TestServiceActivity#onServiceConnected name: " + name + " service: " + service);
            IUserManager manager = IUserManager.Stub.asInterface(service);
            try {
                manager.addUser(new User());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            try {
                Log.i(TAG, "TestServiceActivity#onServiceConnected linkToDeath");
                service.linkToDeath(recipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "TestServiceActivity#onServiceDisconnected name: " + name);
        }
    };

    private IBinder.DeathRecipient recipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.i(TAG, "TestServiceActivity#binderDied");
        }
    };
}
