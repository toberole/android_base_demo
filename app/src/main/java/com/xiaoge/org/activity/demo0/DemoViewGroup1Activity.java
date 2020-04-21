package com.xiaoge.org.activity.demo0;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.view.MotionEvent;

import com.xiaoge.org.R;
import com.xiaoge.org.util.LogUtil;
import com.xiaoge.org.views.DemoViewGroup1;

public class DemoViewGroup1Activity extends AppCompatActivity {
    public static final String TAG = "DemoViewGroup1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_view_group1);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.DemoViewGroup1)
    void DemoViewGroup1() {
        LogUtil.i(TAG, "DemoViewGroup1Activity onclick");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.i(TAG, "DemoViewGroup1Activity#dispatchTouchEvent");

        // Thread.dumpStack();

        return super.dispatchTouchEvent(ev);
    }
}
