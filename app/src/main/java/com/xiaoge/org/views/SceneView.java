package com.xiaoge.org.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoge.org.R;

public class SceneView extends FrameLayout implements View.OnClickListener {
    private Context context;
    private TextView tv5;
    private TextView tv0;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;

    private LinearLayout ll_tvs;

    private static String[] texts = new String[]{
            "默认", "汽车", "智能", "地铁", "大厅"
    };

    private int index = 1;
    private int span;

    public SceneView(Context context) {
        this(context, null);
    }

    public SceneView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SceneView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;

        LayoutInflater.from(context).inflate(R.layout.scene, this, true);

        ll_tvs = findViewById(R.id.ll_tvs);

        tv0 = findViewById(R.id.tv0);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);

        tv0.setOnClickListener(this);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int tv0H = tv0.getHeight();
        int tv5H = tv5.getHeight();
        int w = ll_tvs.getWidth();

        Log.i("xxxx", "tv0 height: " + tv0H + " tv5 height: " + tv5H + " ll_tvs width: " + w);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv1:
                if (index != 1) {
                    scorll(index, 1);
                }
                break;
            case R.id.tv2:
                if (index != 2) {
                    scorll(index, 2);
                }
                break;
            case R.id.tv3:
                if (index != 3) {
                    scorll(index, 3);
                }
                break;
            case R.id.tv4:
                if (index != 4) {
                    scorll(index, 4);
                }
                break;
            case R.id.tv5:
                if (index != 5) {
                    scorll(index, 5);
                }
                break;
            case R.id.tv0:
                break;
            default:
                break;
        }
    }

    private void scorll(int from_index, int to_index) {
        int start_x = (from_index - 1) * span;
        int end_x = (to_index - 1) * span;
        int duration = Math.abs(to_index - from_index) * 250;
        ObjectAnimator translationX = new ObjectAnimator().ofFloat(tv0, "translationX", start_x, end_x);
        translationX.setDuration(duration);

        translationX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                tv0.setText("");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                tv0.setText(texts[to_index - 1]);
                Toast.makeText(context, texts[to_index - 1], Toast.LENGTH_SHORT).show();
            }
        });

        translationX.start();
        index = to_index;
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        span = ll_tvs.getWidth() / 5;
        tv0.setWidth(span);
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
