package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.xiaoge.org.R;
import com.xiaoge.org.view_study.DemoViewGroup;

public class DemoViewGroupActivity extends AppCompatActivity {
    @BindView(R.id.demoViewGroup)
    DemoViewGroup demoViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_view_group);
        ButterKnife.bind(this);

//        demoViewGroup.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.i("DemoViewGroup", "DemoViewGroupActivity OnTouchListener onTouch");
//                return false;
//            }
//        });
//
//        demoViewGroup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("DemoViewGroup", "DemoViewGroupActivity OnClickListener onClick");
//            }
//        });
//
//        demoViewGroup.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Log.i("DemoViewGroup", "DemoViewGroupActivity OnLongClickListener onLongClick");
//                return true;
//            }
//        });
    }

    /**
     * 事件链上所有的view都不处理该事件 那么该事件会被派发到Activity#onTouchEvent
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("DemoViewGroup", "DemoViewGroupActivity OnClickListener onClick");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("DemoViewGroup", "DemoViewGroupActivity dispatchTouchEvent");

        Log.i("DemoViewGroup", "------------------------------------------------------------------");

        // 打印调用链
        Thread.dumpStack();

        Log.i("DemoViewGroup", "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        boolean b = super.dispatchTouchEvent(ev);
        return false;
    }
}
