package com.xiaoge.org.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.LinearLayout;

import com.xiaoge.org.util.DisplayUtil;

import androidx.annotation.Nullable;

public class ScrollRefreshView extends LinearLayout {
    public static final String TAG = ScrollRefreshView.class.getSimpleName();

    private Context context;

    private int start_x;
    private int start_y;

    private int last_x;
    private int last_y;

    private Button head;
    private int head_margin_top;
    private int head_margin_top_threshold;

    public ScrollRefreshView(Context context) {
        super(context);
    }

    public ScrollRefreshView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollRefreshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        init(context);
    }

    private void init(Context ctx) {
        context = ctx;
        head_margin_top = DisplayUtil.dip2px(context, 100);
        head_margin_top_threshold = head_margin_top / 2;
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
                if (y - last_y > 0) {
                    if (y - start_y > head_margin_top_threshold) {
                        setMarginTopDex(head_margin_top);
                    } else {
                        setMarginTopDex(y - last_y);
                    }
                } else {
                    setMarginTop(-head_margin_top);
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

    private void setMarginTop(int margin) {
        LinearLayout.LayoutParams params = (LayoutParams) head.getLayoutParams();
        params.topMargin = margin;
        head.setLayoutParams(params);
    }

    private void setMarginTopDex(int dex) {
        LinearLayout.LayoutParams params = (LayoutParams) head.getLayoutParams();
        int margin_top = params.topMargin;
        margin_top += dex;
        params.topMargin = margin_top;
        head.setLayoutParams(params);
    }
}
