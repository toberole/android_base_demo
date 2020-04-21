package com.xiaoge.org.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

public class HeartbeatView extends View {
    private Paint paint_in;
    private int color_in = Color.parseColor("#F76214");

    private Paint paint_out;
    private int color_out = Color.parseColor("#D4D2D1");

    private int in_start_radius = 20;
    private int out_start_radius = 30;

    private int dex = 10;

    private int w = -1;
    private int h = -1;

    private float amplitude;

    private Context ctx;

    private Anim anim;

    public HeartbeatView(Context context) {
        this(context, null);
    }

    public HeartbeatView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartbeatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        ctx = context;
        in_start_radius = dip2px(context, in_start_radius);
        out_start_radius = dip2px(context, out_start_radius);
        dex = dip2px(context, dex);

        paint_in = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_in.setColor(color_in);

        paint_out = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_out.setColor(color_out);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (w == -1) {
            w = getWidth();
        }

        if (h == -1) {
            h = getHeight();
        }

        long scaleElapsed;
        if (anim != null && (scaleElapsed = SystemClock.uptimeMillis() - anim.time) <= anim.duration) {
            float interpolation = anim.interpolator.getInterpolation(1f * scaleElapsed / anim.duration);
            float currentAmplitude = (anim.endAmplitude - anim.startAmplitude) * interpolation + anim.startAmplitude;
            canvas.drawCircle(w / 2, h / 2, out_start_radius + dex * currentAmplitude, paint_out);
            canvas.drawCircle(w / 2, h / 2, in_start_radius + dex * currentAmplitude, paint_in);
            invalidate();
        } else {
            canvas.drawCircle(w / 2, h / 2, out_start_radius, paint_out);
            canvas.drawCircle(w / 2, h / 2, in_start_radius, paint_in);
        }
    }

    public void smoothToAmplitude(float amplitude) {
        if (anim == null) {
            anim = new Anim();
        }
        anim.startAmplitude = this.amplitude;
        anim.endAmplitude = amplitude;
        anim.time = SystemClock.uptimeMillis();
        invalidate();
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5);
    }

    private class Anim {
        private float startAmplitude;
        private float endAmplitude;
        private long duration = 200;
        private long time = SystemClock.uptimeMillis();
        private Interpolator interpolator = new DecelerateInterpolator();
    }
}
