package com.xiaoge.org.base_test;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.TextView;

public class MyViewPager extends ViewGroup {

    /**
     * 手势识别器
     */
    private GestureDetector gestureDetector;

    /**
     * 实现view平滑滚动的工具类
     */
    private Scroller scroller;

    private int tv_vount = 3;
    private int position = 0;
    private int scrollX;

    public MyViewPager(Context context) {
        this(context, null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        int[] colors = new int[]{Color.GRAY, Color.BLUE, Color.YELLOW};
        for (int i = 0; i < tv_vount; i++) {
            TextView tv = new TextView(context);
            tv.setText(String.valueOf(i));
            tv.setTextColor(Color.RED);
            tv.setBackgroundColor(colors[i]);
            tv.setTextSize(50);
            addView(tv);
        }

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                // 跟随手指滑动
                scrollBy((int) distanceX, 0);
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
        });

        scroller = new Scroller(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            v.layout(i * getWidth(), t, (i + 1) * getWidth(), b);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 触摸事件交给手识别器
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                // 相对于初始位置滑动的距离
                scrollX = getScrollX();
                // 滑动的距离加上屏幕的一半，除以屏幕宽度，
                // 就是当前图片显示的pos.
                // 如果滑动距离超过了屏幕的一半，这个pos就加1
                position = (getScrollX() + getWidth() / 2) / getWidth();
                //滑到最后一张的时候，不能出边界
                if (position >= tv_vount) {
                    position = tv_vount - 1;
                }
                if (position < 0) {
                    position = 0;
                }
                break;
            case MotionEvent.ACTION_UP:
                // 绝对滑动，直接滑到指定的x,y的位置
                // scrollTo(position * getWidth(), 0);

                //滚动，startX, startY为开始滚动的位置，dx,dy为滚动的偏移量
                scroller.startScroll(scrollX, 0, -(scrollX - position * getWidth()), 0);
                //使用invalidate 这个方法会有执行一个回调方法computeScroll 重写这个方法
                invalidate();

                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            postInvalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            v.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
