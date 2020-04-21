package com.xiaoge.org.activity.demo0;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Camera;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xiaoge.org.R;
import com.xiaoge.org.bean.IOnNewUserAdded;
import com.xiaoge.org.bean.IUserManager;
import com.xiaoge.org.bean.User;
import com.xiaoge.org.service.ManagerUserService;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    public static final String TAG = MainActivity.class.getSimpleName();

    private Button btn;
    private Button btn_add_user;
    private Button btn_bind_serice;
    private Button btn_get_users;
    private Button btn_unregister;

    private IUserManager userManager;

    private BusHandler busHandler;

    // 注册给aidl服务端回调 注意写法 IOnNewUserAdded.Stub
    private IOnNewUserAdded listener = new IOnNewUserAdded.Stub() {
        @Override
        public void onAddUser(User u) throws RemoteException {
            Log.i(TAG, "IOnNewUserAdded#onAddUser user name: " + u.name + " age: " + u.age);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn_test);
        btn_bind_serice = findViewById(R.id.btn_bind_serice);
        btn_add_user = findViewById(R.id.btn_add_user);
        btn_get_users = findViewById(R.id.btn_get_users);
        btn_unregister = findViewById(R.id.btn_unregister);

        btn.setOnClickListener(this);
        btn_bind_serice.setOnClickListener(this);
        btn_add_user.setOnClickListener(this);
        btn_get_users.setOnClickListener(this);
        btn_unregister.setOnClickListener(this);

        btn.getLeft();
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i(TAG, "btn.setOnTouchListener#onTouch ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i(TAG, "btn.setOnTouchListener#onTouch ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i(TAG, "btn.setOnTouchListener#onTouch ACTION_UP");
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        Log.i(TAG, "btn.setOnTouchListener#onTouch ACTION_CANCEL");
                        break;
                    default:
                        break;
                }

                return true;
            }
        });

        busHandler = new BusHandler();
        busHandler.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        busHandler.post();
    }

    private void test() {
        startActivity(null);
        startService(null);
        bindService(null, null, 0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        }, null);
        sendBroadcast(null);

        Camera camera = new Camera();
        camera.translate(1.0f, 1.0f, 1.0f);

        IntentService intentService;

        getContentResolver().query(null, null, null, null, null);

        ObjectAnimator animator = ObjectAnimator.ofFloat(null, "xx", 1);
        animator.addUpdateListener(null);
        animator.start();
        animator.cancel();

        Dialog dialog = null;
        dialog.setContentView(null);

        Toast toast = Toast.makeText(null, "hello", Toast.LENGTH_SHORT);
        toast.setView(null);
        toast.show();

//        Lock lock = new ReentrantLock();
//        lock.lock();
    }

    private void test1() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "MainActivity_X#onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG, "MainActivity_X#onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i(TAG, "MainActivity_X#onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, "MainActivity_X#onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(TAG, "MainActivity_X#onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        /**
         * 注意 此种方法需要使用RemoteCallbackList，系统提供的专门用于删除跨进程的listener的接口
         */
        if (userManager != null && userManager.asBinder().isBinderAlive()) {
            try {
                userManager.unRegisterListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        Log.i(TAG, "MainActivity_X#onDestroy");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                Log.i(TAG, "btn clicked");
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_unregister:
                if (userManager != null && userManager.asBinder().isBinderAlive()) {
                    try {
                        userManager.unRegisterListener(listener);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btn_add_user:
                if (userManager != null) {

                    try {
                        User user = new User();
                        user.age = 33;
                        user.name = "client_name";
                        userManager.addUser(user);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btn_get_users:
                if (userManager != null) {
                    try {
                        List<User> users = userManager.getUsers();
                        for (User u : users) {
                            Log.i(TAG, "user name: " + u.name + " age: " + u.age);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btn_bind_serice:
                Log.i("xxxx", "btn_bind_serice");
                Intent bind_service = new Intent(MainActivity.this, ManagerUserService.class);
                MainActivity.this.bindService(bind_service, this, BIND_AUTO_CREATE);
                break;
            default:
                break;
        }
    }

    @Override
    /**
     * 当调用的client与service在同一个进程里面，service即为本地实现
     * 否则就是android.os.BinderProxy
     */
    public void onServiceConnected(ComponentName name, IBinder service) {
        // 打印查看具体的service类[同进程与非同进程的区别]
        Log.i("xxxxx", "onServiceConnected ComponentName: " + name.toShortString());
        Log.i("xxxxx", "onServiceConnected service: " + service);
        Log.i("xxxxx", "onServiceConnected listener: " + listener);

        userManager = IUserManager.Stub.asInterface(service);

        // 通信异常中断 会回调deathRecipient
        service.unlinkToDeath(deathRecipient, 0);

        try {
            userManager.registerListener(listener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        userManager = null;
    }

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.i(TAG, "IBinder.DeathRecipient#binderDied");
        }
    };

    private class BusHandler extends Thread {
        private Handler handler;

        @Override
        public void run() {
            Log.i(TAG, "BusHandler run");

            Looper.prepare();
            handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    Log.i(TAG, "BusHandler handleMessage");
                    return false;
                }
            });
            Looper.loop();
        }

        void post() {
            if (handler != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "BusHandler handler.post");
                    }
                });

                handler.sendEmptyMessage(1);
            }
        }
    }
}
