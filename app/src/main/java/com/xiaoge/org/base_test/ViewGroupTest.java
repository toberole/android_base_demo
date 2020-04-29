package com.xiaoge.org.base_test;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoge.org.util.LogUtil;

import androidx.annotation.Nullable;

public class ViewGroupTest extends ViewGroup {
    public static final String TAG = ViewGroupTest.class.getSimpleName()+"-yyyyy";

    public ViewGroupTest(Context context) {
        this(context, null);
    }

    public ViewGroupTest(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewGroupTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.i(TAG, "onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        LogUtil.i(TAG, "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtil.i(TAG, "onDraw");
    }
}
