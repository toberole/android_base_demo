package com.xiaoge.org.activity.demo;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.xiaoge.org.R;
import com.xiaoge.org.fragment.Fragment1;
import com.xiaoge.org.fragment.Fragment2;
import com.xiaoge.org.util.LogUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment 切换方式：
 * replace方式
 * transaction.replace(R.id.content, IndexFragment);
 * add－hide－show方式
 * transaction.add(R.id.content, IndexFragment);
 * transaction.hide(otherfragment);
 * transaction.show(IndexFragment);
 */

public class FragmentActivity2 extends AppCompatActivity {
    public static final String TAG = "Fragment1";

    private FrameLayout fl_container;
    private FragmentManager fragmentManager;
    private Fragment fragment1;
    private Fragment fragment2;

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.i(TAG, "FragmentActivity2#onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i(TAG, "FragmentActivity2#onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.i(TAG, "FragmentActivity2#onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.i(TAG, "FragmentActivity2#onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG, "FragmentActivity2#onDestroy");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment1);
        fl_container = findViewById(R.id.fl_container);
        LogUtil.i(TAG, "FragmentActivity2#onCreate");
        ButterKnife.bind(this);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                LogUtil.i(TAG, "FragmentActivity2#onBackStackChanged");
            }
        });
    }

    @OnClick(R.id.btn_1)
    void btn1() {
        if (fragment1 == null) {
            fragment1 = new Fragment1();
        }

        fragmentManager.
                beginTransaction()
                .replace(R.id.fl_container, fragment1, "f1")
                .commit();
    }

    @OnClick(R.id.btn_2)
    void btn2() {
        if (null == null) {
            fragment2 = new Fragment2();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.fl_container, fragment2, "f2")
                .commit();
    }

    @OnClick(R.id.btn_3)
    void btn3() {
        fragmentManager.popBackStack();
    }
}
