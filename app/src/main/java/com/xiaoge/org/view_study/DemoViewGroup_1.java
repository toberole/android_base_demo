package com.xiaoge.org.view_study;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class DemoViewGroup_1 extends ViewGroup {
    public static final String TAG = DemoViewGroup_1.class.getSimpleName();

    public DemoViewGroup_1(Context context) {
        this(context, null);
        Log.i(TAG, "DemoViewGroup 1");
    }

    public DemoViewGroup_1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        Log.i(TAG, "DemoViewGroup 2");
    }

    public DemoViewGroup_1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i(TAG, "DemoViewGroup 3");
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "DemoViewGroup onInterceptTouchEvent ");

        boolean b = super.onInterceptTouchEvent(ev);
        return b;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "DemoViewGroup dispatchTouchEvent ");
        boolean b;
        b = super.dispatchTouchEvent(ev);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "DemoViewGroup onTouchEvent ");
        boolean b = super.onTouchEvent(event);
        return b;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "DemoViewGroup onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "DemoViewGroup onSizeChanged");
    }
}
