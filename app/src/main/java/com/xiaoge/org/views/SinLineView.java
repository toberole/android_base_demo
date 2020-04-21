package com.xiaoge.org.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xiaoge.org.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class SinLineView extends View {
    public static final String TAG = SinLineView.class.getSimpleName();

    public static final double PI_I = Math.PI * 2 / 360;

    private Context ctx;

    private List<Path> paths = new ArrayList<>();

    private Paint h_line_paint;
    private int h_line_diameter = 2;
    private String h_line_color = "#FF0000";

    private Paint h_sin_paint;
    private float h_sin_diameter = 1.5f;
    private String h_sin_color = "#00FF00";

    private int volume = 50;
    private int max_volume = 100;
    private int sensitivity = 100;

    private int width;
    private int height;

    public static final float DEX = 5;

    public SinLineView(Context context) {
        this(context, null);
    }

    public SinLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SinLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        ctx = context;
        h_line_diameter = DisplayUtil.dip2px(ctx, h_line_diameter);
        h_sin_diameter = DisplayUtil.dip2px(ctx, h_sin_diameter);

        h_line_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        h_line_paint.setStrokeWidth(h_line_diameter);
        h_line_paint.setColor(Color.parseColor(h_line_color));

        h_sin_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        h_sin_paint.setStyle(Paint.Style.STROKE);
        h_sin_paint.setStrokeWidth(h_sin_diameter);
        h_sin_paint.setColor(Color.parseColor(h_sin_color));

        for (int i = 0; i < 20; i++) {
            paths.add(new Path());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
        Log.i(TAG, "onDraw width: " + width + " height: " + height);

        drawHorizontalLine(canvas);
        drawSinLine(canvas);
    }

    private void drawHorizontalLine(Canvas canvas) {
        canvas.drawLine(0, height / 2 - h_line_diameter / 2, width, height / 2 + h_line_diameter / 2, h_line_paint);
    }

    private void drawSinLine(Canvas canvas) {
        for (int i = 0; i < paths.size(); i++) {
            paths.get(i).reset();
            paths.get(i).moveTo(0, height / 2);
        }

        int period = 360;
        for (float i = 0; i < period; i++) {
            float sin = (float) Math.sin(i * PI_I);
            for (int j = 0; j < paths.size(); j++) {
                Path path = paths.get(j);
                float x1 = 1.0f * i * width / period;
                float dexY = (float) (1.0 * sensitivity * volume / max_volume * sin);
                float y1 = (float) (height / 2 + dexY + Math.sqrt(0 * 20));
                path.lineTo(x1, y1);
            }
        }

        for (int n = 0; n < paths.size(); n++) {
            if (n == paths.size() - 1) {
                h_sin_paint.setAlpha(255);
            }
            canvas.drawPath(paths.get(n), h_sin_paint);
        }
    }

    public void setVolume(int v) {
        if (v != volume) {
            this.volume = volume;
            postInvalidate();
        }
    }

    public int getVolume() {
        return volume;
    }

    public int getMaxVolume() {
        return max_volume;
    }

    public void setMaxVolume(int max_volume) {
        this.max_volume = max_volume;
    }

    public int getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(int sensitivity) {
        this.sensitivity = sensitivity;
    }
}
