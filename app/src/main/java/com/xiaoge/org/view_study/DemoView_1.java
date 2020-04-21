package com.xiaoge.org.view_study;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.xiaoge.org.R;
import com.xiaoge.org.util.DisplayUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DemoView_1 extends View {
    public static final String TAG = DemoView_1.class.getSimpleName();

    private Context ctx;
    private Paint paint;

    public DemoView_1(Context context) {
        this(context, null);
        Log.i(TAG, "DemoView 1");
    }

    public DemoView_1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        Log.i(TAG, "DemoView 2");
    }

    public DemoView_1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
        Log.i(TAG, "DemoView 3");
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        ctx = context;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw");
        Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_test);
        if (bitmap != null) {
            //第一张
            canvas.drawBitmap(bitmap, 0, 0, paint);
            //第二张
            canvas.drawBitmap(bitmap, 0, getHeight() - bitmap.getHeight(), paint);
            //旋转45
            canvas.rotate(45, getWidth() / 2, getHeight() / 2);
            //第三张
            canvas.drawBitmap(bitmap, getWidth() / 2 - bitmap.getWidth() / 2, getHeight() / 2 - bitmap.getHeight() / 2, paint);
        }
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
