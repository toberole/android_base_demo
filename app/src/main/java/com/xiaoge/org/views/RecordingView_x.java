package com.xiaoge.org.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.UiThread;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class RecordingView_x extends View {
    private Paint paint_in;
    private int color_in = Color.parseColor("#F76214");

    private Paint paint_out;
    private int color_out = Color.parseColor("#D4D2D1");

    private Paint paint_arc;
    private int color_arc = Color.parseColor("#F76214");
    private int arc_width = 8;

    private int in_start_radius = 80;
    private int in_end_radius = 40;

    private int roundRect_width = 30;

    private int out_start_radius = 100;
    private int out_end_radius = 120;

    private long startTime = 0;
    private int duration = 500;
    private int record_duration = 1000 * 15;

    private double in_radius_sp = 0;
    private double out_radius_sp = 0;

    private int w = -1;
    private int h = -1;

    private Context context;
    private boolean isStop = false;
    private boolean isStartAnimation = false;

    private Listener listener;

    public RecordingView_x(Context context) {
        this(context, null);
    }

    public RecordingView_x(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecordingView_x(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        in_start_radius = dip2px(context, in_start_radius);
        in_end_radius = dip2px(context, in_end_radius);

        out_start_radius = dip2px(context, out_start_radius);
        out_end_radius = dip2px(context, out_end_radius);

        in_radius_sp = (in_start_radius - in_end_radius) * 1.0 / duration;
        out_radius_sp = (out_end_radius - out_start_radius) * 1.0 / duration;

        roundRect_width = dip2px(context, roundRect_width);

        arc_width = dip2px(context, arc_width);

        paint_in = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_in.setColor(color_in);

        paint_out = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_out.setColor(color_out);

        paint_arc = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_arc.setStrokeWidth(arc_width);
        paint_arc.setStyle(Paint.Style.STROKE);
        paint_arc.setColor(color_arc);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(dip2px(context, 250), dip2px(context, 250));
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

        int time = (int) (System.currentTimeMillis() - startTime);

        if (time <= duration && isStartAnimation && !isStop) {
            int temp_in_radius = (int) (in_start_radius - in_radius_sp * time);
            int temp_out_radius = (int) (out_start_radius + out_radius_sp * time);

            Log.i("zw", "temp_in_radius: " + temp_in_radius + " temp_out_radius: " + temp_out_radius);

            canvas.drawCircle(w / 2, h / 2, temp_out_radius, paint_out);
            canvas.drawCircle(w / 2, h / 2, temp_in_radius, paint_in);

            postInvalidate();
        } else if (time > duration && isStartAnimation && !isStop && time <= record_duration + duration) {
            Log.i("zw", "time > duration && !isStop && time <= record_duration");

            canvas.drawCircle(w / 2, h / 2, out_end_radius, paint_out);
            double p = (time - duration) * 1.0 / record_duration;
            float sweepAngle = (float) (p * 360);
            drawArc(canvas, sweepAngle);

            RectF rectF = new RectF();
            rectF.left = out_end_radius - in_end_radius + in_end_radius - roundRect_width;
            rectF.top = out_end_radius - in_end_radius + in_end_radius - roundRect_width;
            rectF.right = rectF.left + 2 * roundRect_width;
            rectF.bottom = rectF.top + 2 * roundRect_width;
            canvas.drawRoundRect(rectF, 15, 15, paint_in);
            if (listener != null) {
                listener.onProgress((float) p);
            }
            postInvalidate();
        } else if (isStartAnimation && time > record_duration + duration) {
            canvas.drawCircle(w / 2, h / 2, out_end_radius, paint_out);

            drawArc(canvas, 360);

            RectF rectF = new RectF();
            rectF.left = out_end_radius - in_end_radius + in_end_radius - roundRect_width;
            rectF.top = out_end_radius - in_end_radius + in_end_radius - roundRect_width;
            rectF.right = rectF.left + 2 * roundRect_width;
            rectF.bottom = rectF.top + 2 * roundRect_width;
            canvas.drawRoundRect(rectF, 15, 15, paint_in);

            isStartAnimation = false;
            isStop = true;

            if (listener != null) {
                listener.onFinish();
            }
        } else {
            canvas.drawCircle(w / 2, h / 2, out_start_radius, paint_out);
            canvas.drawCircle(w / 2, h / 2, in_start_radius, paint_in);
        }
    }

    private void drawArc(Canvas canvas, float sweepAngle) {
        RectF oval = new RectF();
        oval.left = (float) (arc_width + 0.5);
        oval.top = (float) (arc_width + 0.5);
        oval.right = 2 * out_end_radius;
        oval.bottom = 2 * out_end_radius;
        canvas.drawArc(oval, -90, sweepAngle, false, paint_arc);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @UiThread
    public void start() {
        startTime = System.currentTimeMillis();
        isStop = false;
        isStartAnimation = true;
        postInvalidate();
    }

    @UiThread
    public void stop() {
        isStartAnimation = false;
        isStop = true;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5);
    }

    public interface Listener {
        void onFinish();

        void onProgress(float p);
    }
}
