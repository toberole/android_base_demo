package com.xiaoge.org.views;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class VerticalViewPager extends ViewPager {
    public VerticalViewPager(@NonNull Context context) {
        super(context);
        init(context);
    }

    public VerticalViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setPageTransformer(true, new DefaultTramsformer());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = super.onInterceptTouchEvent(ev);
        swapEvent(ev);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    private MotionEvent swapEvent(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();

        //将Y轴的移动距离转变为X轴的移动距离
        float swappedX = ev.getY() / height * width;

        //将X轴的移动距离转变为Y轴的移动距离
        float swappedY = ev.getX() / width * height;

        // 重新设置event的位置
        ev.setLocation(swappedX, swappedY);
        return ev;
    }

    private static class DefaultTramsformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(@NonNull View view, float position) {
            float alpha = 0;
            if (position >= 0 && position <= 1) {
                alpha = 1 - position;
            } else if (position > -1 && position < 0) {
                alpha = position + 1;
            }

            view.setAlpha(alpha);

            float transX = view.getWidth() * position;
            view.setTranslationX(transX);
            float transY = position * view.getHeight();
            view.setTranslationY(transY);
        }
    }
}
