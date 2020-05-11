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
 * 用surfaceview 模拟帧动画
 * 避免帧动画加载过多的图片发生OOM
 */
public class FrameSurfaceView extends BaseSurfaceView {
    public static final int INVALID_BITMAP_INDEX = Integer.MAX_VALUE;

    private List<Integer> pics = new ArrayList<>();
    //帧图片
    private Bitmap frameBitmap;
    //帧索引
    private int bitmapIndex = INVALID_BITMAP_INDEX;

    private Paint paint = new Paint();

    private BitmapFactory.Options options = new BitmapFactory.Options();

    //帧图片原始大小
    private Rect srcRect;
    //帧图片目标大小
    private Rect dstRect = new Rect();

    private int defaultWidth;
    private int defaultHeight;

    public FrameSurfaceView(Context context) {
        this(context, null);
    }

    public FrameSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FrameSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        super.init(context, attrs, defStyleAttr);
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

        //默认情况下，计算第一帧图片的原始大小
        getBitmapDimension(pics.get(0));
    }

    private void getBitmapDimension(int id) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(this.getResources(), id, options);
        defaultWidth = options.outWidth;
        defaultHeight = options.outHeight;
        srcRect = new Rect(0, 0, defaultWidth, defaultHeight);
        requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        dstRect.set(0, 0, getWidth(), getHeight());
    }

    @Override
    protected void onFrameDrawFinish() {
        //在一帧绘制完后，直接回收它
        recycleOneFrame();
    }

    //回收帧
    private void recycleOneFrame() {
        if (frameBitmap != null) {
            frameBitmap.recycle();
            frameBitmap = null;
        }
    }

    @Override
    protected void onFrameDraw(Canvas canvas) {
        //绘制一帧前需要先清画布，否则所有帧都叠在一起同时显示
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

    //绘制一帧，是张Bitmap
    private void drawOneFrame(Canvas canvas) {
        frameBitmap = decodeOriginBitmap(getResources(), pics.get(bitmapIndex), options);
        canvas.drawBitmap(frameBitmap, srcRect, dstRect, paint);
        bitmapIndex++;
    }

    private Bitmap decodeOriginBitmap(Resources resources, Integer id, BitmapFactory.Options options) {
        return BitmapFactory.decodeResource(resources, id, options);
    }

    private void onFrameAnimationEnd() {
        reset();
    }

    private void reset() {
        bitmapIndex = INVALID_BITMAP_INDEX;
    }

    //帧动画是否结束
    private boolean isFinish() {
        return bitmapIndex >= pics.size();
    }

    //帧动画是否开始
    private boolean isStart() {
        return bitmapIndex != INVALID_BITMAP_INDEX;
    }

    //开始播放帧动画
    public void start() {
        bitmapIndex = 0;
    }

    private void clearCanvas(Canvas canvas) {
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    }
}
