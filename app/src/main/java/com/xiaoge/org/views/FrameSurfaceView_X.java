package com.xiaoge.org.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * 优化FrameSurfaceView
 */
public class FrameSurfaceView_X extends BaseSurfaceView {
    public static final int INVALID_BITMAP_INDEX = Integer.MAX_VALUE;
    private List<Integer> pics = new ArrayList<>();
    private Bitmap frameBitmap;
    private int bitmapIndex = INVALID_BITMAP_INDEX;
    private Paint paint = new Paint();
    private BitmapFactory.Options options;
    private Rect srcRect;
    private Rect dstRect = new Rect();
    private int defaultWidth;
    private int defaultHeight;

    public FrameSurfaceView_X(Context context) {
        this(context, null);
    }

    public FrameSurfaceView_X(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FrameSurfaceView_X(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        super.init(context, attrs, defStyleAttr);
        // 定义解析Bitmap参数为可变类型 这样才能复用Bitmap
        options = new BitmapFactory.Options();
        options.inMutable = true;

        frameBitmap.getAllocationByteCount();
        frameBitmap.getByteCount();


    }

    public void setDuration(int duration) {
        int frameDuration = duration / pics.size();
        setFrameDuration(frameDuration);
    }

    public void setPics(List<Integer> pics) {
        if (pics == null || pics.size() == 0) {
            return;
        }
        this.pics = pics;
        getBitmapDimension(pics.get(0));
    }

    private void getBitmapDimension(int id) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(this.getResources(), id, options);
        defaultWidth = options.outWidth;
        defaultHeight = options.outHeight;
        srcRect = new Rect(0, 0, defaultWidth, defaultHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        dstRect.set(0, 0, getWidth(), getHeight());
    }


    @Override
    protected void onFrameDrawFinish() {
        //每帧绘制完毕后不再回收
//        recycle();
    }

    public void recycle() {
        if (frameBitmap != null) {
            frameBitmap.recycle();
            frameBitmap = null;
        }
    }

    @Override
    protected void onFrameDraw(Canvas canvas) {
        clearCanvas(canvas);
        if (!isStart()) {
            return;
        }
        if (!isFinish()) {
            drawOneFrame(canvas);
        } else {
            onFrameAnimationEnd();
        }
    }

    private void drawOneFrame(Canvas canvas) {
        frameBitmap = decodeOriginBitmap(getResources(), pics.get(bitmapIndex), options);
        //复用上一帧Bitmap的内存
        options.inBitmap = frameBitmap;
        canvas.drawBitmap(frameBitmap, srcRect, dstRect, paint);
        bitmapIndex++;
    }

    private void onFrameAnimationEnd() {
        reset();
    }

    private void reset() {
        bitmapIndex = INVALID_BITMAP_INDEX;
    }

    private boolean isFinish() {
        return bitmapIndex >= pics.size();
    }

    private boolean isStart() {
        return bitmapIndex != INVALID_BITMAP_INDEX;
    }

    public void start() {
        bitmapIndex = 0;
    }

    private void clearCanvas(Canvas canvas) {
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    }

    private Bitmap decodeOriginBitmap(Resources resources, Integer id, BitmapFactory.Options options) {
        return BitmapFactory.decodeResource(resources, id, options);
    }

    protected int getDefaultWidth() {
        return defaultWidth;
    }

    protected int getDefaultHeight() {
        return defaultHeight;
    }
}
