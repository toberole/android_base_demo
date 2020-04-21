package com.xiaoge.org.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * 注意 draw方法和onTouch方法不在同一个线程
 * 注意数据共享时需要同步处理
 * UI线程与draw线程可以采用消息队列通信
 */
public class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    public static final String TAG = CustomSurfaceView.class.getSimpleName();

    private SurfaceHolder holder;
    private Canvas canvas;
    private volatile boolean isDrawing;
    private Paint paint;
    private Path path;

    public CustomSurfaceView(Context context) {
        this(context, null);
    }

    public CustomSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        holder = getHolder();
        holder.addCallback(this);

        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);

        path = new Path();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder_) {
        Log.i(TAG, "surfaceCreated " + Thread.currentThread().getName());

        startRender();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(TAG, "surfaceChanged " + Thread.currentThread().getName());
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(TAG, "surfaceDestroyed " + Thread.currentThread().getName());
        holder.removeCallback(this);

        stopRender();
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        while (isDrawing) {
            draw();
        }
        long end = System.currentTimeMillis();
        if (end - start < 100) {//保证线程运行时间不少于100ms
            try {
                Thread.sleep(100 - (end - start));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void draw() {
        try {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            canvas.drawPath(path, paint);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent " + Thread.currentThread().getName());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 清空之前的轨迹
                // path.reset();
                updatePath4MoveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                updatePath4LineTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }

    private void updatePath4MoveTo(float x, float y) {
        path.moveTo(x, y);
    }

    private void updatePath4LineTo(float x, float y) {
        path.lineTo(x, y);
    }

    private synchronized void startRender() {
        isDrawing = true;
        new Thread(this).start();
    }

    private synchronized void stopRender() {
        isDrawing = false;
    }

    private class MQ extends LinkedBlockingDeque<Point> {

    }
}
