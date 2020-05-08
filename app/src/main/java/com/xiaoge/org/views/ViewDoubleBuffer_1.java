package com.xiaoge.org.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class ViewDoubleBuffer_1 extends View {
    private Paint paint;
    private Path path;

    //绘图是用于缓存绘制过程的bitmap
    private Bitmap bufferBitmap;
    private Canvas bufferCanvas;
    private int preX;
    private int preY;

    public ViewDoubleBuffer_1(Context context) {
        this(context, null);
    }

    public ViewDoubleBuffer_1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewDoubleBuffer_1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (bufferBitmap != null) {
            bufferBitmap.recycle();
        }

        bufferBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        if (bufferCanvas == null) {
            // bufferBitmap相当于画布了 使用bufferCanvas绘制的内容都绘制到了bufferBitmap
            bufferCanvas = new Canvas(bufferBitmap);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bufferBitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //重置path 清除path中保存的数据
                path.reset();
                preX = x;
                preY = y;
                //将起点移动到此处
                path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                // 画贝塞尔曲线
                // 贝塞尔曲线比bufferCanvas.drawLine();画出来的曲线更平滑
                path.quadTo(preX, preY, x, y);
                //通知重绘制view
                bufferCanvas.drawPath(path, paint);

                // 触发重新绘制
                invalidate();

                preY = y;
                preX = x;
                break;
            case MotionEvent.ACTION_UP:
                // 抬起时将path绘制在buffer中保存
                bufferCanvas.drawPath(path, paint);
                invalidate();
                break;
        }
        return true;
    }
}
