package com.xiaoge.org.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.xiaoge.org.util.LogUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DemoViewGroup1 extends FrameLayout {
    public static final String TAG = DemoViewGroup1.class.getSimpleName();

    public DemoViewGroup1(@NonNull Context context) {
        super(context);
    }

    public DemoViewGroup1(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DemoViewGroup1(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtil.i(TAG, "DemoViewGroup1#onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.i(TAG, "DemoViewGroup1#dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.i(TAG, "DemoViewGroup1#onTouchEvent");
        return super.onTouchEvent(event);
    }
}
