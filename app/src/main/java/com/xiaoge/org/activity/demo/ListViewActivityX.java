package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaoge.org.R;
import com.xiaoge.org.service.IntentService_Test;
import com.xiaoge.org.test.MyTask;
import com.xiaoge.org.test.TT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ListViewActivityX extends AppCompatActivity {
    @BindView(R.id.lv_tt)
    ListView listView;

    List<String> datas = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_x);

        ButterKnife.bind(this);
        initData();
        initView();


    }

    private void test() {
        Lock lock = new ReentrantLock();
        ((ReentrantLock) lock).hasQueuedThreads();
        lock.lock();
        lock.lock();
        Log.i("lock", "---------------");
        int holdCount = ((ReentrantLock) lock).getHoldCount();
        Log.i("lock", "--------------- " + holdCount);
        lock.unlock();
        lock.unlock();
    }

    private void test3() {
        Object object;

        TT tt = new TT() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        };
    }

    private void test2() {
        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };

        MyTask myTask = new MyTask();
        myTask.execute("hello", "world");
        myTask.cancel(true);

        // myTask.executeOnExecutor();

        Future future = null;
        future.cancel(true);
        BlockingDeque blockingDeque;


        IntentService intentService;
    }

    private void test1() {
        HashMap<String, String> map;
        ConcurrentHashMap<String, String> concurrentHashMap;
    }

    private static class Task implements Callable<String> {
        @Override
        public String call() throws Exception {
            // 模拟真实事务的处理过程，这个过程是非常耗时的。
            Thread.sleep(5000);
            return "call return " + Thread.currentThread().getName();
        }
    }

    private void testFutrue() throws Exception {
        List<Future<String>> futures = new ArrayList<Future<String>>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println("已经提交资源申请");

        for (int i = 0; i < 10; i++) {
            futures.add(executorService.submit(new Task()));
        }

        for (Future<String> future : futures) {
            if (!future.isDone()) {
                System.out.println("资源还没有准备好");
            }
            System.out.println(future.get());
        }

        executorService.shutdown();
    }

    private void initView() {
        adapter = new MyAdapter();
        listView.setAdapter(adapter);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListViewActivityX.this, IntentService_Test.class);
                ListViewActivityX.this.startService(intent);
                ListViewActivityX.this.startService(intent);
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            datas.add(String.valueOf(i));
        }
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = new TextView(ListViewActivityX.this);
            tv.setText(datas.get(position));
            return tv;
        }
    }
}
