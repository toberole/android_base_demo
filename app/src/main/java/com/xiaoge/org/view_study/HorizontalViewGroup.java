package com.xiaoge.org.view_study;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xiaoge.org.util.DisplayUtil;

/**
 * 自定义ViewGroup是需要处理子View的margin和自身padding、wrap_content
 */
public class HorizontalViewGroup extends ViewGroup {
    public static final String TAG = HorizontalViewGroup.class.getSimpleName();

    private static final long DURATION = 800;

    private Context ctx;

    private int default_width = 200;
    private int default_height = 200;

    private int index = 0;

    private float lastX;
    private float lastY;
    private int childCount;

    private volatile boolean isScrolling = false;

    public HorizontalViewGroup(Context context) {
        this(context, null);
    }

    public HorizontalViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.ctx = context;
        default_width = DisplayUtil.dip2px(ctx, default_width);
        default_height = DisplayUtil.dip2px(ctx, default_height);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int w_mode = MeasureSpec.getMode(widthMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h_mode = MeasureSpec.getMode(heightMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);

        childCount = getChildCount();
        Log.i(TAG, "childCount: " + childCount);

        if (childCount != 0) {
            int w_p = 0;
            for (int i = 0; i < childCount; i++) {
                View v = getChildAt(0);
                w_p += v.getMeasuredWidth();
            }
            setMeasuredDimension(w_p, h);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        View v = getChildAt(0);
        int w = v.getMeasuredWidth();
        int h = v.getMeasuredHeight();

        Log.i(TAG, "w: " + w + " h: " + h);

        for (int i = 0; i < childCount; i++) {
            v = getChildAt(i);
            int left = w * i;
            int top = 0;
            int right = w * (i + 1);
            int bottom = h;
            v.layout(left, top, right, bottom);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);
        boolean b = true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getRawX();
                lastY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                float x = event.getRawX();
                if (x - lastX < 0 && lastX - x > 200) {
                    scroll_left();
                } else if (x - lastX >= 200) {
                    scroll_right();
                }
                b = false;

                lastX = 0;
                lastY = 0;
                break;
            default:
                break;
        }

        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent");
        return super.onTouchEvent(event);
    }

    private void scroll_left() {
        Log.i(TAG, "scroll_left index: " + index + " isScrolling: " + isScrolling);
        int w = getWidth();
        float start = getTranslationX();
        float end = getTranslationX() - w / childCount;
        Log.i(TAG, "start: " + start + " end: " + end);

        if (index == childCount - 1) {
            Toast.makeText(ctx, "已经是最后一页", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isScrolling) return;

        isScrolling = true;

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "translationX", start, end);
        objectAnimator.setDuration(DURATION);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(TAG, "scroll_left onAnimationEnd");
                lastX = 0;
                lastY = 0;
                index++;
                isScrolling = false;
                Toast.makeText(ctx, "index: " + index, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        objectAnimator.start();
    }

    private void scroll_right() {
        Log.i(TAG, "scroll_right index: " + index + " isScrolling: " + isScrolling);
        int w = getWidth();
        float start = getTranslationX();
        float end = getTranslationX() + w / childCount;
        Log.i(TAG, "start: " + start + " end: " + end);

        if (index == 0) {
            Toast.makeText(ctx, "已经是第一页", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isScrolling) return;

        isScrolling = true;

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "translationX", start, end);
        objectAnimator.setDuration(DURATION);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(TAG, "scroll_right onAnimationEnd");
                lastX = 0;
                lastY = 0;
                index--;
                isScrolling = false;
                Toast.makeText(ctx, "index: " + index, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        objectAnimator.start();
    }
}
