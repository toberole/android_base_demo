package com.xiaoge.org.view_study;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoge.org.activity.demo.ScrollConflictActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class LinearLayout_Scroll extends LinearLayout {
    public static final String TAG = LinearLayout_Scroll.class.getSimpleName();

    private int w;
    private int h;

    private int index = 0;
    private int max_index = 2;

    private float lastX;
    private float lastY;
    private Context context;

    public LinearLayout_Scroll(Context context) {
        this(context, null);
    }

    public LinearLayout_Scroll(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearLayout_Scroll(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.context = context;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        float x = ev.getX();
        float y = ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;

                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(x - lastX) > Math.abs(y - lastY)) {
                    intercept = true;

                    if (x - lastX > 0) {
                        // 右滑动
                        Log.i(TAG, "水平右滑动 " + intercept);

                        if (index == 0) {
                            Toast.makeText(context, "已经是第一页了", Toast.LENGTH_SHORT).show();
                        } else {
                            scrollPage_2Right(index, index + 1);
                        }
                    } else {
                        // 左滑动
                        Log.i(TAG, "水平左滑动 " + intercept);

                        if (index == (max_index - 1)) {
                            Toast.makeText(context, "已经是最后一页了", Toast.LENGTH_SHORT).show();
                        } else {
                            scrollPage_2Left(index, index + 1);
                        }
                    }

                } else {
                    intercept = false;
                    Log.i(TAG, "垂直滑动 " + intercept);
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                intercept = false;
                break;
            default:
                break;
        }

        Log.i(TAG, "intercept: " + intercept);
        return intercept;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;

        Log.i(TAG, "w: " + w + " h: " + h);
    }

    private void scrollPage_2Left(int out_index, int in_index) {
        ListView v0 = (ListView) getChildAt(0);
        TextView v1 = (TextView) getChildAt(1);

        Log.i(TAG, "v0_x: " + v0.getX() + " v0_y: " + v0.getY() + " translationX: " + v0.getTranslationX());
        Log.i(TAG, "v1_x: " + v1.getX() + " v1_y: " + v1.getY() + " translationX: " + v1.getTranslationX());

        long duration = 1500;

        ObjectAnimator objectAnimator_v0 = ObjectAnimator.ofFloat(v0, "translationX", 0, -w);
        objectAnimator_v0.setDuration(duration);

        ObjectAnimator objectAnimator_v1 = ObjectAnimator.ofFloat(v1, "translationX", 0, -w);
        objectAnimator_v1.setDuration(duration);
        objectAnimator_v1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(TAG, " onAnimationEnd ");
                index++;


                Log.i(TAG, "onAnimationEnd v0_x: " + v0.getX() + ",v0_y: " + v0.getY() + ",translationX: " + v0.getTranslationX());
                Log.i(TAG, "onAnimationEnd v1_x: " + v1.getX() + ",v1_y: " + v1.getY() + ",translationX: " + v1.getTranslationX());



//                List<String> datas1 = new ArrayList<>();
//                for (int i = 0; i < 100; i++) {
//                    datas1.add("02-" + i + "-" + System.currentTimeMillis());
//                }
//                ArrayAdapter<String> adapter1 = new ArrayAdapter(context,
//                        android.R.layout.simple_list_item_1);
//                adapter1.addAll(datas1);
//                v1.setAdapter(adapter1);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        objectAnimator_v0.start();
        objectAnimator_v1.start();
    }

    private void scrollPage_2Right(int index, int i) {

    }
}
