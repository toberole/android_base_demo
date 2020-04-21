package com.xiaoge.org.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.UiThread;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class RecordingView extends View {
    private Paint paint1;
    private int color1;

    private Paint paint2;
    private int color2;

    private Paint paint3;
    private int color3;

    private int start_radius = 80;
    private int end_radius = 40;

    private int start_strokeWidth = 10;
    private int end_strokeWidth = 30;

    private long startTime = 0;

    private int duration = 5000;

    private double radius_sp = 0;
    private double stroke_sp = 0;

    private boolean isStartAnimation = false;

    private Context context;

    public RecordingView(Context context) {
        this(context, null);
    }

    public RecordingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecordingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        start_radius = dip2px(context, start_radius);
        end_radius = dip2px(context, end_radius);

        start_strokeWidth = dip2px(context, start_strokeWidth);
        end_strokeWidth = dip2px(context, end_strokeWidth);

        radius_sp = (start_radius - end_radius) * 1.0 / duration;
        stroke_sp = (end_strokeWidth - start_strokeWidth) * 1.0 / duration;

        Log.i("zw", "radius_sp: " + radius_sp + " stroke_sp: " + stroke_sp);

        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        color1 = Color.parseColor("#F76214");
        paint1.setColor(color1);

        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        color2 = Color.parseColor("#D4D2D1");
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(start_strokeWidth);
        paint2.setColor(color2);

        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        color3 = Color.parseColor("#00FF00");
    }

    private int w = -1;
    private int h = -1;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (w == -1) {
            w = getWidth();
        }

        if (h == -1) {
            h = getHeight();
        }

        int time = (int) (System.currentTimeMillis() - startTime);
        if (true) {
            canvas.drawCircle(w / 2, h / 2, start_radius + 30, paint3);
            canvas.drawCircle(w / 2, h / 2, start_radius, paint1);
            return;
        }

        if (time <= duration && isStartAnimation) {
            int temp_start_radius = (int) (start_radius - radius_sp * time);
            int temp_strokeWidth = (int) (start_strokeWidth + stroke_sp * time);
            paint2.setStrokeWidth(temp_strokeWidth);

            Log.i("zw", "temp_start_radius: " + temp_start_radius + " temp_strokeWidth: " + temp_strokeWidth);

            canvas.drawCircle(w / 2, h / 2, temp_start_radius, paint1);
            canvas.drawCircle(w / 2, h / 2, temp_start_radius, paint2);

            if (time != duration) {
                postInvalidate();
            }
        } else if (time > duration && isStartAnimation) {
            startTime = 0;
            isStartAnimation = false;

            canvas.drawCircle(w / 2, h / 2, end_radius, paint1);
            paint2.setStrokeWidth(end_strokeWidth);
            canvas.drawCircle(w / 2, h / 2, end_radius, paint2);
        } else {
            canvas.drawCircle(w / 2, h / 2, start_radius, paint1);
            canvas.drawCircle(w / 2, h / 2, start_radius, paint2);
        }
    }

    @UiThread
    public void startAnimation() {
        startTime = System.currentTimeMillis();
        isStartAnimation = true;
        postInvalidate();
    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
