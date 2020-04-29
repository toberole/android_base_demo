package com.xiaoge.org.base_test;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.xiaoge.org.util.LogUtil;

import androidx.annotation.Nullable;

public class ViewTest extends View {
    public static final String TAG = ViewTest.class.getSimpleName() + "-xxxx";

    public ViewTest(Context context) {
        this(context, null);
    }

    public ViewTest(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.i(TAG, "onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogUtil.i(TAG, "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtil.i(TAG, "onDraw");
    }
}
