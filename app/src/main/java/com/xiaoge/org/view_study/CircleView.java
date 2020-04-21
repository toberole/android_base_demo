package com.xiaoge.org.view_study;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xiaoge.org.R;
import com.xiaoge.org.util.DisplayUtil;

import androidx.annotation.Nullable;

/**
 * 自定义View
 * 需要自己处理padding和wrap_content[默认效果就是match_parent]
 * margin 不需要自己处理[父布局会处理margin]
 */
public class CircleView extends View {
    public static final String TAG = CircleView.class.getSimpleName();

    private Context ctx;
    private Paint paint;
    private int color = Color.RED;

    private int default_width = 100;
    private int default_height = 150;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.ctx = context;
        default_width = DisplayUtil.dip2px(ctx, default_width);
        default_height = DisplayUtil.dip2px(ctx, default_height);

        // 处理自定义属性
        if (null != attrs) {
            TypedArray typedArray = ctx.obtainStyledAttributes(attrs, R.styleable.CircleView);
            color = typedArray.getColor(R.styleable.CircleView_color, Color.RED);
            default_width = (int) typedArray.getDimension(R.styleable.CircleView_u_w, default_width);
            default_height = (int) typedArray.getDimension(R.styleable.CircleView_u_w, default_height);
            typedArray.recycle();
        }
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "CircleView#onDraw");
        float x = getX();
        float y = getY();
        int w = getWidth();
        int h = getHeight();

        // 处理padding
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        Log.i(TAG, "paddingLeft: " + paddingLeft);
        Log.i(TAG, "paddingTop: " + paddingTop);
        Log.i(TAG, "paddingRight: " + paddingRight);
        Log.i(TAG, "paddingBottom: " + paddingBottom);

        Log.i(TAG, "w: " + w + "," + "h: " + h + "," + "x: " + x + "," + "y: " + y);

        // padding 是算作View的宽和高
        // 所以在画View的Content时 可以用的空间需要去掉padding
        int w1 = w - paddingLeft - paddingRight;
        int h1 = h - paddingTop - paddingBottom;
        float radius = w1 < h1 ? w1 / 2 : h1 / 2;
        canvas.drawCircle(w / 2, h / 2, radius, paint);
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        Log.i(TAG, "CircleView#onDrawForeground");
    }

    @Override
    /**
     * 需要处理warp_content
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "CircleView#onMeasure");
        int w_mode = MeasureSpec.getMode(widthMeasureSpec);
        int w_size = MeasureSpec.getSize(widthMeasureSpec);

        int h_mode = MeasureSpec.getMode(heightMeasureSpec);
        int h_size = MeasureSpec.getSize(heightMeasureSpec);

        if (w_mode == MeasureSpec.AT_MOST) {
            w_size = default_width;
        }

        if (h_mode == MeasureSpec.AT_MOST) {
            h_size = default_height;
        }

        setMeasuredDimension(w_size, h_size);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "CircleView#onLayout");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.i(TAG, "CircleView#onAttachedToWindow");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "CircleView#onSizeChanged");
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Log.i(TAG, "CircleView#onSaveInstanceState");
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        Log.i(TAG, "CircleView#onRestoreInstanceState");
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        Log.i(TAG, "CircleView#onFocusChanged");
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.i(TAG, "CircleView#onWindowFocusChanged");
    }
}
