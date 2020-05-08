package com.xiaoge.org.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;


import androidx.annotation.Nullable;

/**
 * 用View模拟 Surface的双缓冲机制[实际情况比较复杂]
 * <p>
 * 双缓冲的核心技术就是先通过setBitmap方法将要绘制的所有的图形绘制到一个Bitmap上，
 * 然后再来调用drawBitmap方法绘制出这个Bitmap，显示在屏幕上
 * <p>
 * 提高绘图性能
 * 可以在屏幕上展示绘图的过程
 * 保存绘制的历史
 */
public class ViewDoubleBuffer extends View implements Runnable {
    private int width = 300;
    private int height = 500;

    private Paint ui_Paint = null;

    private Paint buffer_Paint = null;

    /* 创建一个缓冲区 */
    private Bitmap buffer_Bitmap = null;

    /* 创建Canvas对象 */
    private Canvas buffer_Canvas = null;

    public ViewDoubleBuffer(Context context) {
        this(context, null);
    }

    public ViewDoubleBuffer(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewDoubleBuffer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        ui_Paint = new Paint();
        buffer_Paint = new Paint();
        buffer_Paint.setColor(Color.GREEN);

        /* 创建屏幕大小的缓冲区 */
        buffer_Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        /* 创建Canvas */
        buffer_Canvas = new Canvas();

        /*
            将内容绘制在buffer_Bitmap上
            buffer_Bitmap相当于画布
        */
        buffer_Canvas.setBitmap(buffer_Bitmap);

        buffer_Canvas.drawBitmap(buffer_Bitmap, 0, 0, buffer_Paint);

        /* 开启线程 */
        new Thread(this).start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        synchronized (draw_lock) {
            /* 将buffer_Bitmap显示到屏幕上 */
            canvas.drawBitmap(buffer_Bitmap, 0, 0, ui_Paint);
        }
    }

    private Object draw_lock = new Object();

    public Path path;

    /**
     * 异步绘制 然后postInvalidate
     * 触发onDraw 绘制到UI
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                synchronized (draw_lock) {
                    buffer_Canvas.drawPath(path, buffer_Paint);
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
            postInvalidate();
        }
    }

    public void drawPath(Path path) {
        synchronized (draw_lock) {
            this.path = path;
        }
    }
}
