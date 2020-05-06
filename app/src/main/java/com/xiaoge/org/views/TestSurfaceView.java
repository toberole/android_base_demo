package com.xiaoge.org.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 1. class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback 
 * 2. SurfaceView.getHolder()获得SurfaceHolder对象 
 * 3. SurfaceHolder.addCallback(callback)添加回调函数 
 * 4. SurfaceHolder.lockCanvas()获得Canvas对象并锁定画布 
 * 5. Canvas绘画 
 * 6. SurfaceHolder.unlockCanvasAndPost(Canvas canvas)结束锁定画图，并提交改变，将图形显示。
 */
public class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    // 控制sufaceview
    private SurfaceHolder surfaceHolder;

    // 声明一张画布
    private Canvas canvas;

    private Paint paint;

    private volatile boolean flag = false;

    private Thread thread;


    public TestSurfaceView(Context context) {
        this(context, null);
    }

    public TestSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);

        setFocusable(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);
        flag = true;
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
        surfaceHolder.removeCallback(this);
    }

    @Override
    public void run() {
        while (flag) {
            try {
                synchronized (surfaceHolder) {
                    drawUI();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);//结束锁定画图，并提交改变。
                }
            }
        }
    }

    private void drawUI() {
        canvas = surfaceHolder.lockCanvas();// 获得画布对象，开始对画布画画        
        if (null != canvas) {
            // todo draw ui
            // ......
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}


