package com.xiaoge.org.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xiaoge.org.R;


public class AITipView extends FrameLayout {
    private Context context;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            index++;
            startAnimation_();
        }
    };

    private int index = 0;

    String[] tips = new String[]{
            "今天天气", "星期几", "好热啊", "其余同上"
    };
    private TextView tv1;
    private TextView tv2;
    private Animation enter;
    private Animation exit;
    private volatile boolean stop;

    public AITipView(Context context) {
        this(context, null);
    }

    public AITipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AITipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.ai_tip, this, true);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);

        tv1.setText(tips[index % tips.length]);
        tv2.setText("");
        // tv2.setText(tips[(index + 1) % tips.length]);
    }

    private void startAnimation_() {
        tv1.startAnimation(exit);
        tv2.startAnimation(enter);
        if (!stop) {
            handler.sendEmptyMessageDelayed(100, 2500);
        }
    }

    public void start() {
        stop = false;

        enter = AnimationUtils.loadAnimation(context, R.anim.ai_tip_enter);
        exit = AnimationUtils.loadAnimation(context, R.anim.ai_tip_exit);

        AnimatorListener_ ll = new AnimatorListener_("tv1");
        exit.setAnimationListener(ll);
        AnimatorListener_ l = new AnimatorListener_("tv2");
        enter.setAnimationListener(l);

        handler.sendEmptyMessageDelayed(100, 1000);
    }

    private AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            tv1.setTranslationY(0);
            tv2.setTranslationY(0);
        }
    };

    public void start1() {
        ObjectAnimator tv1_translation = new ObjectAnimator().ofFloat(tv1, "translationY", 0, -100);
        tv1_translation.addListener(animatorListenerAdapter);

        ObjectAnimator tv1_alpha = ObjectAnimator.ofFloat(tv1, "alpha", 0f, 1f, 0f);
        tv1_alpha.setDuration(2000);
        ObjectAnimator tv2_alpha = ObjectAnimator.ofFloat(tv1, "alpha", 1f, 0f, 1f);
        tv1_alpha.setDuration(2000);


        tv1_translation.setDuration(2000);
        ObjectAnimator tv2_translation = new ObjectAnimator().ofFloat(tv2, "translationY", 0, -100);
        tv2_translation.setDuration(2000);

        tv1_translation.start();
        tv2_translation.start();
    }

    public void stop() {
        stop = true;

        if (exit != null) {
            exit.cancel();
        }
        if (enter != null) {
            enter.cancel();
        }
    }

    private class AnimatorListener_ implements Animation.AnimationListener {
        private String msg;

        public AnimatorListener_(String msg) {
            this.msg = msg;
        }

        @Override
        public void onAnimationStart(Animation animation) {
            if ("tv1".equals(msg)) {
                Log.i("xxxxx", "tv2 index: " + ((index) % tips.length));
                tv2.setVisibility(View.VISIBLE);
                tv2.setText(tips[(index) % tips.length]);
            }
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if ("tv1".equals(msg)) {
                Log.i("xxxxx", "tv1 index: " + (index % tips.length));
                tv1.setText(tips[index % tips.length]);
                tv2.setText("");
                tv2.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
