package com.xiaoge.org.view_study;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.xiaoge.org.kotlin.demo.P;
import com.xiaoge.org.util.DisplayUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DemoView extends View {
    public static final String TAG = DemoView.class.getSimpleName();

    private Paint paint;

    public DemoView(Context context) {
        super(context);
        Log.i(TAG, "DemoView 1");
    }

    public DemoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "DemoView 2");
    }

    public DemoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i(TAG, "DemoView 3");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw");

        if (paint == null) {
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.RED);
        }

//        paint.setTextSize(DisplayUtil.dip2px(getContext(), 20));
//
//        /**
//         * 绘制文本的时候，参数X,Y并不是绘制的起点，而是文字的底部
//         * 这个x,y 是左下角的坐标
//         * 获取text的长度 使用 paint.measureText(text)
//         * 获取text的高度 使用float height = paint.ascent()+paint.descent()
//         *
//         */
//        float x = -DisplayUtil.dip2px(getContext(), 30);
//        float y = 0;
//        canvas.drawText("hello view", x, y, paint);

        int w = getWidth();
        int h = getHeight();
        int r = w < h ? w / 2 : h / 2;
        canvas.drawCircle(w / 2, h / 2, r, paint);

//        canvas.save();
//        canvas.restore();
//        canvas.translate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure");

        int w_mode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        if (w_mode == MeasureSpec.AT_MOST) {
            width = DisplayUtil.dip2px(getContext(), 200);
        }

        int h_mode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (h_mode == MeasureSpec.AT_MOST) {
            height = DisplayUtil.dip2px(getContext(), 300);
        }

        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 相对于当前View左上角的x和y坐标
        float x = event.getX();
        float y = event.getY();

        // 相对手机屏幕左上角的x和y
        float rawX = event.getRawX();
        float rawY = event.getRawY();

        Log.i(TAG, "x: " + x + " y: " + y);
        Log.i(TAG, "rawX: " + rawX + " rawY: " + rawY);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "ACTION_CANCEL");
                break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.i(TAG, "onAttachedToWindow");
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.i(TAG, "onWindowFocusChanged");
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        Log.i(TAG, "onVisibilityChanged");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i(TAG, "onDetachedFromWindow");
    }
}
