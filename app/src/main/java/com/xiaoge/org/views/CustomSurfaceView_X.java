package com.xiaoge.org.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * 注意 draw方法和onTouch方法不在同一个线程
 * 注意数据共享时需要同步处理
 * UI线程与draw线程可以采用消息队列通信
 */
public class CustomSurfaceView_X extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    public static final String TAG = CustomSurfaceView_X.class.getSimpleName();

    private SurfaceHolder holder;
    private Canvas canvas;
    private volatile boolean isDrawing;
    private Paint paint;
    private Path path;

    BlockingDeque<MyPoint> event_queue = new LinkedBlockingDeque<>();

    public CustomSurfaceView_X(Context context) {
        this(context, null);
    }

    public CustomSurfaceView_X(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSurfaceView_X(Context context, AttributeSet attrs, int defStyleAttr) {
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
        while (isDrawing) {
            MyPoint point = getPoint();
            if (point != null) {
                path.lineTo(point.x, point.y);
                draw();
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

    private synchronized void updatePath4LineTo(float x, float y) {
        MyPoint point = new MyPoint(x, y);
        event_queue.offer(point);
    }

    private synchronized MyPoint getPoint() {
        try {
            return event_queue.poll(5, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    private synchronized void startRender() {
        isDrawing = true;
        new Thread(this).start();
    }

    private synchronized void stopRender() {
        isDrawing = false;
    }

    private class MyPoint {
        public float x;
        public float y;

        public MyPoint(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
