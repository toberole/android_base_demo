package com.xiaoge.org.view_study;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Scroll水平滑动
 * 内部嵌入一个listview
 */
public class ScrollConflictView extends HorizontalScrollView {
    public static final String TAG = ScrollConflictView.class.getSimpleName();

    public ScrollConflictView(Context context) {
        super(context);
    }

    public ScrollConflictView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollConflictView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float lastX;
    private float lastY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        float x = ev.getX();
        float y = ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;

                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(x - lastX) > Math.abs(y - lastY)) {
                    intercept = true;
                    Log.i(TAG, "水平滑动 " + intercept);
                } else {
                    intercept = false;
                    Log.i(TAG, "垂直滑动 " + intercept);
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                intercept = false;
                break;
            default:
                break;
        }

        Log.i(TAG, "intercept: " + intercept);
        return intercept;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
