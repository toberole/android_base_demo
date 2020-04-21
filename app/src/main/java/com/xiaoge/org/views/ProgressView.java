package com.xiaoge.org.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

public class ProgressView extends View {

    public static final int STATE_LOADING = 1;
    public static final int STATE_STOP = 0;

    Paint paint = new Paint();
    RectF rectF = new RectF();
    RectF rectF2 = new RectF();
    Path path = new Path();
    private int startAngle;
    private float percent;
    private int gap;
    private int strokeWidth;
    private int color = Color.WHITE;
    private int backgroundColor = Color.GRAY;
    private int angleGap;
    private int triangleLength;
    private int state;
    private Anim anim;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        rectF.set(strokeWidth, strokeWidth, right - left - strokeWidth, bottom - top - strokeWidth);
        rectF2.set(gap + strokeWidth, gap + strokeWidth, right - left - gap - strokeWidth, bottom - top - gap - strokeWidth);
        float temp = (float) (Math.sqrt(3) / 6 * triangleLength);
        path.reset();
        path.moveTo((right - left) / 2f - temp, (bottom - top - triangleLength) / 2f);
        path.lineTo((right - left) / 2f - temp, (bottom - top + triangleLength) / 2f);
        path.lineTo((right - left) / 2f + temp * 2, (bottom - top) / 2f);
        path.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float sweepAngle;
        paint.setAntiAlias(true);
        //画背景
        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawArc(rectF, 0, 360, true, paint);

        //画内容
        paint.setColor(color);
        if (state == STATE_STOP) {
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawPath(path, paint);
            sweepAngle = 360;
        } else {
            long scaleElapsed;
            if (anim != null && (scaleElapsed = SystemClock.uptimeMillis() - anim.time) <= anim.duration) {
                float interpolation = anim.interpolator.getInterpolation(1f * scaleElapsed / anim.duration);
                float currentPercent = (anim.endPercent - anim.startPercent) * interpolation + anim.startPercent;
                canvas.drawArc(rectF2, 270, 360 * currentPercent, true, paint);
                percent = currentPercent;
                sweepAngle = 360;
                invalidate();
            } else {
                if (percent == 0) {
                    sweepAngle = 360 - angleGap;
                    setStartAngle(startAngle + 10);
                } else {
                    canvas.drawArc(rectF2, 270, 360 * percent, true, paint);
                    sweepAngle = 360;
                }
                if (anim != null && anim.listener != null) {
                    anim.listener.onAnimationFinish();
                    anim.listener = null;
                }
            }
        }
        //画圆框
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawArc(rectF, startAngle, sweepAngle, false, paint);
    }

    private void setStartAngle(int startAngle) {
        if (percent == 0) {
            if (this.startAngle != startAngle) {
                invalidate();
            }
        }
        this.startAngle = startAngle;
    }

    public void setPercent(float percent) {
        if (percent > 1f) {
            percent = 1f;
        } else if (percent < 0) {
            percent = 0;
        }
        if (this.percent != percent) {
            invalidate();
            this.percent = percent;
        }
    }

    public void smoothToPercent(float percent, AnimListener listener) {
        if (percent > this.percent) {
            if (anim == null) {
                anim = new Anim();
            }
            anim.startPercent = this.percent;
            anim.endPercent = percent;
            anim.time = SystemClock.uptimeMillis();
            anim.listener = listener;
            invalidate();
        }
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        if (this.state != state) {
            invalidate();
            this.state = state;
        }
    }

    public interface AnimListener {
        void onAnimationFinish();
    }

    private class Anim {
        private float startPercent;
        private float endPercent;
        private long duration = 200; // How long the anim takes
        private long time = SystemClock.uptimeMillis(); // Start time
        private Interpolator interpolator = new DecelerateInterpolator();
        private AnimListener listener;
    }
}
