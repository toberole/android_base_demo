package com.xiaoge.org.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class ViewGroup_X extends LinearLayout {
    public ViewGroup_X(Context context) {
        super(context);
    }

    public ViewGroup_X(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroup_X(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
