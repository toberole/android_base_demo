package com.xiaoge.org.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.xiaoge.org.util.LogUtil;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;

/**
 * dragView 随着手指移动
 */
public class ViewDragHelper_View extends RelativeLayout {

    private ViewDragHelper viewDragHelper;

    /**
     * 被拖拽的view
     */
    private View dragView;

    public ViewDragHelper_View(Context context) {
        this(context, null);
    }

    public ViewDragHelper_View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewDragHelper_View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        dragView = getChildAt(0);
        if (dragView != null) {
            viewDragHelper = ViewDragHelper.create(
                    this,
                    1,
                    new ViewDragHelperCallback(dragView));
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    /**
     * clampViewPositionHorizontal -> clampViewPositionVertical -> onViewPositionChanged
     */
    private class ViewDragHelperCallback extends ViewDragHelper.Callback {
        public final String TAG = ViewDragHelperCallback.class.getSimpleName();

        private View dragView;

        public ViewDragHelperCallback(View dragView) {
            this.dragView = dragView;
        }

        @Override
        /**
         * tryCaptureView
         * 用于自由判定哪个子控件可被拖拽
         * 返回true代表可拖拽，false则禁止
         */
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return dragView == child;
        }

        @Override
        /**
         * 子控件水平方向位置改变时触发
         * left:即将移动到的位置
         * dx:x方向的改变值
         */
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            LogUtil.i(TAG, "clampViewPositionHorizontal left: " + left + ",dx: " + dx);
            return left;
        }

        @Override
        /**
         * 子控件竖直方向位置改变时触发
         * top:即将移动到的位置
         * dy:y方向的改变值
         */
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            LogUtil.i(TAG, "clampViewPositionVertical top: " + top + ",dy: " + dy);
            return top;
        }

        @Override
        /**
         * 手指松开时触发
         *  @param xvel X velocity of the pointer as it left the screen in pixels per second.
         *  @param yvel Y velocity of the pointer as it left the screen in pixels per second.
         */
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            LogUtil.i(TAG, "onViewReleased xvel: " + xvel + ",yvel: " + yvel);
            super.onViewReleased(releasedChild, xvel, yvel);
        }

        @Override
        /**
         * 子控件位置改变时触发（包括X和Y轴方向）
         */
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            LogUtil.i(TAG, "onViewPositionChanged left: " + left + ",top: " + top + ",dx: " + dx + ",dy: " + dy);
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }
    }
}
