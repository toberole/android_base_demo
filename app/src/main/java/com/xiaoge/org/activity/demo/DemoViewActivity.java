package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;

import com.xiaoge.org.R;
import com.xiaoge.org.view_study.CircleView;
import com.xiaoge.org.view_study.DemoView;

public class DemoViewActivity extends AppCompatActivity {
    public static final String TAG = DemoViewActivity.class.getSimpleName();

    @BindView(R.id.btn_test)
    Button btn_test;

    @BindView(R.id.demoView)
    DemoView demoView;

    @BindView(R.id.cv)
    CircleView cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_view);
        ButterKnife.bind(this);

        HandlerThreadTest1 handlerThreadTest1 = new HandlerThreadTest1();
        handlerThreadTest1.start();
        Log.i(TAG, "------------: " + String.valueOf(findViewById(android.R.id.content)));
    }

    @OnClick(R.id.demoView)
    void demoView() {
        Log.i(TAG, "demoView clicked");
        btn_test.scrollBy(0, 20);
    }

    @OnClick(R.id.btn_test)
    void btn_test() {
        Log.i(TAG, "btn_test clicked");
        demoView.scrollBy(0, 20);
    }

    private class HandlerThreadTest extends Thread {
        @Override
        public void run() {
            HandlerThread handlerThread = new HandlerThread("");
            handlerThread.getLooper();
            handlerThread.start();

            Looper.prepare();
            Handler handler = new Handler(Looper.myLooper());
            Message message = Message.obtain();
//            message.setAsynchronous(true);
            handler.post(null);
            handler.postAtTime(null, 0);
            handler.sendEmptyMessage(0);
            handler.sendEmptyMessageAtTime(0, 0);
            handler.sendEmptyMessageDelayed(0, 0);
            handler.sendMessage(message);

            Looper.loop();
        }
    }

    /**
     * 模拟HandlerThread
     */
    private class HandlerThreadTest1 extends Thread {
        private volatile Looper looper;

        public Looper getLooper() {
            synchronized (HandlerThreadTest1.this) {
                while (looper == null) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            return looper;
        }

        @Override
        public void run() {
            synchronized (HandlerThreadTest1.this) {
                Looper.prepare();
                looper = Looper.myLooper();
                notifyAll();
            }

            Looper.loop();
        }
    }
}
