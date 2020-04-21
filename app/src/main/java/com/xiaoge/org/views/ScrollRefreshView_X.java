package com.xiaoge.org.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.LinearLayout;

import com.xiaoge.org.util.DisplayUtil;

import androidx.annotation.Nullable;

public class ScrollRefreshView_X extends LinearLayout {
    public static final String TAG = ScrollRefreshView_X.class.getSimpleName();

    private Context context;

    private int start_x;
    private int start_y;

    private int last_x;
    private int last_y;

    private Button head;
    private int head_margin_top;
    private int head_margin_top_threshold;

    public ScrollRefreshView_X(Context context) {
        this(context, null);
    }

    public ScrollRefreshView_X(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollRefreshView_X(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        init(context);
    }

    private void init(Context ctx) {
        context = ctx;
        head_margin_top = DisplayUtil.dip2px(context, 100);
        head_margin_top_threshold = head_margin_top / 2;
        Log.i(TAG, "head_margin_top: " + head_margin_top + " head_margin_top_threshold: " + head_margin_top_threshold);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        head = (Button) getChildAt(0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean ret = super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                start_x = (int) event.getX();
                start_y = (int) event.getY();
                last_x = start_x;
                last_y = start_y;
                Log.i(TAG, "ACTION_DOWN start_x: " + start_x + " start_y: " + start_y);
                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();

                Log.i(TAG, "ACTION_MOVE cur_x: " + x + " cur_y: " + y);
                Log.i(TAG, "ACTION_MOVE y - last_y: " + (y - last_y));

                int head_cur_margin = getCurMargin();
                Log.i(TAG, "head_cur_margin: " + head_cur_margin);

                if (head_cur_margin < 0) {
                    if (y - start_y > head_margin_top_threshold) {
                        showHead();
                    } else {
                        if (y - last_y > 0) {
                            setMarginTopDex(y - last_y);
                        }
                    }
                } else {
                    if (y - last_y < 0) {
                        hideHead();
                    }
                }

                last_x = x;
                last_y = y;
                break;
            case MotionEvent.ACTION_UP:
                start_x = 0;
                start_y = 0;
                break;
            default:
                break;
        }
        return true;
    }

    private void hideHead() {
        LayoutParams params = (LayoutParams) head.getLayoutParams();
        ValueAnimator animator = ValueAnimator.ofFloat(params.topMargin, -head_margin_top);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float f = (float) animation.getAnimatedValue();
                params.topMargin = (int) f;
                head.setLayoutParams(params);
                Log.i("xxxxx", "hideHead after: " + getCurMargin());
            }
        });
        animator.start();
    }

    private void showHead() {
        LayoutParams params = (LayoutParams) head.getLayoutParams();
        int cur = params.topMargin;
        ValueAnimator animator = ValueAnimator.ofFloat(cur, 0);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float f = (float) animation.getAnimatedValue();
                params.topMargin = (int) f;
                head.setLayoutParams(params);
                Log.i("xxxxx", "showHead after: " + getCurMargin());
            }
        });
        animator.start();
    }

    private void setMarginTopDex(int dex) {
        LayoutParams params = (LayoutParams) head.getLayoutParams();
        int margin_top = params.topMargin;
        margin_top += dex;
        params.topMargin = margin_top;
        head.setLayoutParams(params);
    }

    private int getCurMargin() {
        LayoutParams params = (LayoutParams) head.getLayoutParams();
        int margin_top = params.topMargin;
        return margin_top;
    }
}
