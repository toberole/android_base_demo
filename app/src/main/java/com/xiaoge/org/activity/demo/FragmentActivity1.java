package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.widget.LinearLayout;

import com.cat.zeus.page.ZeusFragment;
import com.xiaoge.org.R;
import com.xiaoge.org.fragment.Fragment2;
import com.xiaoge.org.util.LogUtil;

import java.io.File;

/**
 * A actiivty 启动 B activity
 * A#onPause -> B#onCreate -> B#onStart -> B#onResume -> A#onStop
 */

public class FragmentActivity1 extends AppCompatActivity {
    public static final String TAG = "Fragment1";

    private LinearLayout ll_container;

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.i(TAG, "FragmentActivity1#onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i(TAG, "FragmentActivity1#onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.i(TAG, "FragmentActivity1#onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.i(TAG, "FragmentActivity1#onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG, "FragmentActivity1#onDestroy");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment1);
        ll_container = findViewById(R.id.ll_container);
        LogUtil.i(TAG, "FragmentActivity1#onCreate");
        ButterKnife.bind(this);
        // 统计调用时间
        // sd卡根目录/Android/data/你的包名/files/dmtrace-x.trace
        File file = getExternalFilesDir(null);//注意该方法是Context的方法,故需要调用的地方如果不在Activity中,需要传入Context调用
        Debug.startMethodTracing(file + "/dmtrace.trace");
        int i = test();
        Debug.stopMethodTracing();

        // 指定路径
        file = new File(Environment.getExternalStorageDirectory(), "dmtrace-x.trace");
        LogUtil.i("getExternalFilesDir", "file path: " + file);
        Debug.startMethodTracing(file.getAbsolutePath());
        i = test();
        Debug.stopMethodTracing();
    }

    private int test() {
        int i = 0;
        for (int j = 0; j < 10000; j++) {
            i += j;
        }

        return i;
    }

    @OnClick(R.id.btn_1)
    void btn1() {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment f = new Fragment1();
//        fragmentManager.
//                beginTransaction()
//                .replace(R.id.ll_container, f, "f1")
//                .commit();

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment2 f = ZeusFragment.newInstance(Fragment2.class);
        fragmentManager.
                beginTransaction()
                .replace(R.id.ll_container, f, "f1")
                .commit();
    }

    @OnClick(R.id.btn_2)
    void btn2() {
        Intent intent = new Intent(FragmentActivity1.this, FragmentActivity2.class);
        FragmentActivity1.this.startActivity(intent);
    }
}
