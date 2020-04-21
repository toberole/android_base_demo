package com.xiaoge.org.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.xiaoge.org.R;

/**
 * 自定义view 继承自View
 * 需要处理wrap_content 和 padding
 * 在onMeasure中处理wrap_content
 * 在onDraw中处理padding
 */
public class M_View extends View {
    public static final String TAG = M_View.class.getSimpleName();

    private int default_width = 400;
    private int default_height = 500;

    private Paint paint;
    private int color;

    public M_View(Context context) {
        this(context, null);
    }

    public M_View(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public M_View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 解析自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.M_View);
        color = typedArray.getColor(R.styleable.M_View_m_view_color, Color.RED);
        typedArray.recycle();

        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 处理 wrap_content
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(default_width, default_height);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(default_width, heightSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, default_height);
        }

        Log.i(TAG, "M_View#onMeasure");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int padding_left = getPaddingLeft();
        int padding_right = getPaddingRight();
        int padding_top = getPaddingTop();
        int padding_bottom = getPaddingBottom();
        // 处理padding
        // view的总宽度减去padding 剩余的部分才是View内容可使用的宽度
        int width = getWidth() - padding_left - padding_right;
        int height = getHeight() - padding_top - padding_bottom;

        Log.i(TAG, "M_View width = " + getWidth());
        Log.i(TAG, "M_View height = " + getHeight());

        if (width <= 0) width = default_width;
        if (height <= 0) height = default_height;

        int radius = Math.min(width, height) / 2;

        canvas.drawCircle(padding_left + width / 2, padding_top + height / 2, radius, paint);

        Log.i(TAG, "M_View#onDraw");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "M_View#onLayout");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "M_View#onSizeChanged");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.i(TAG, "M_View#onAttachedToWindow");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i(TAG, "M_View#onDetachedFromWindow");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
