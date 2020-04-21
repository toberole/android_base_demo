package com.xiaoge.org.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaoge.org.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.Nullable;

public class ScrollRefreshView_XX extends LinearLayout implements AbsListView.OnScrollListener, View.OnTouchListener {
    public static final String TAG = ScrollRefreshView_XX.class.getSimpleName();

    private ListAdapter adapter;

    private View head_view;
    private View bottom_view;
    private ListView listView;

    private int head_view_height;
    private int threshold;

    private int concurrentFirstVisibleItem;
    private boolean isRefreshing = false;

    private boolean isPullDown = false;

    private Handler handler = new Handler();

    private TextView tv_last_update_time;

    private String last_update_time;

    private OnRefreshListener refreshListener;

    public ScrollRefreshView_XX(Context context) {
        this(context, null);
    }

    public ScrollRefreshView_XX(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollRefreshView_XX(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScrollRefreshView);
        int head_layout = typedArray.getInt(R.styleable.ScrollRefreshView_head, 0);
        int bottom_layout = typedArray.getInt(R.styleable.ScrollRefreshView_bottom, 0);
        typedArray.recycle();

        if (head_layout != 0) {
            head_view = View.inflate(context, head_layout, null);
        } else {
            head_view = View.inflate(context, R.layout.head, null);
        }

        if (bottom_layout != 0) {
            bottom_view = View.inflate(context, bottom_layout, null);
        } else {
            bottom_view = View.inflate(context, R.layout.bottom, null);
        }

        listView = new ListView(context);

        setOrientation(VERTICAL);

        addView(head_view);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        listView.setLayoutParams(params);
        addView(listView);
        addView(bottom_view);

        tv_last_update_time = head_view.findViewById(R.id.tv_last_update_time);

        listView.setOnScrollListener(this);
        init();
    }

    private void init() {
        head_view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                head_view_height = head_view.getHeight();
                LinearLayout.LayoutParams params = (LayoutParams) head_view.getLayoutParams();
                params.topMargin = -head_view_height;
                threshold = head_view_height / 2;

                // Log.i(TAG, "head_view_height: " + head_view_height);
                return true;
            }
        });

        setOnTouchListener(this);
    }

    private int mLastYIntercept;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    /**
     * 一旦拦截之后 后续的事件序列 就不回调此方法了
     * 直接调用自己的onTouchEvent方法
     */
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent");
        boolean interceppt = false;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastYIntercept = (int) ev.getRawY();
                interceppt = false;
                break;
            case MotionEvent.ACTION_MOVE:
                boolean b = isNeedInterceptTouchEvent(ev);
                if (b) {
                    interceppt = true;
                } else {
                    interceppt = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                interceppt = false;
                break;
            default:
                break;
        }

        return interceppt;
    }

    private boolean isNeedInterceptTouchEvent(MotionEvent ev) {
        int y = (int) ev.getRawY();
        int dex = y - mLastYIntercept;

        boolean ret = false;

        if (dex > 0 && concurrentFirstVisibleItem == 0) {
            ret = true;
        }

        return ret;
    }

    /**
     * onTouch是OnTouchListener
     * 当设置了OnTouchListener，touch事件首先会交给OnTouchListener.onTouch
     * onTouch返回true表示消费了该事件，那么此事件不会交给onTouchEvent处理
     * onTouch返回flalse表示没有消费此事件，此事件会继续交给onTouchEvent处理
     * 优先级：OnTouchListener.onTouch > onTouchEvent
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i(TAG, "onTouch");
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int y = (int) event.getRawY();
                int dex = y - mLastYIntercept;
                if (dex >= threshold && !isRefreshing) {
                    isRefreshing = true;
                    showHead();
                    mLastYIntercept = 0;
                }
                break;
            case MotionEvent.ACTION_UP:
                mLastYIntercept = 0;
                break;
            default:
                break;
        }
        return true;
    }

    private void showHead() {
        if (!TextUtils.isEmpty(last_update_time)) {
            tv_last_update_time.setText(last_update_time);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        last_update_time = dateFormat.format(new Date());

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) head_view.getLayoutParams();
        int from = params.topMargin;
        int to = 0;
        ValueAnimator animator = ValueAnimator.ofFloat(from, to);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float f = (float) animation.getAnimatedValue();
                params.topMargin = (int) f;
                head_view.setLayoutParams(params);

                if (f == 0) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideHeadAnimation();
                        }
                    }, 500);
                    refreshData();
                }
            }
        });
        animator.start();
    }

    private void refreshData() {
        if (refreshListener != null) {
            refreshListener.onPullDownRefresh();
        } else {
            refreshListener.onPullUpRefresh();
        }
    }

    private void hideHeadAnimation() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) head_view.getLayoutParams();
        int from = 0;
        int to = -head_view_height;
        ValueAnimator animator = ValueAnimator.ofFloat(from, to);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float f = (float) animation.getAnimatedValue();
                params.topMargin = (int) f;
                head_view.setLayoutParams(params);

                if (f == to) {
                    isRefreshing = false;
                }
            }
        });
        animator.start();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (concurrentFirstVisibleItem > firstVisibleItem) {
            Log.i("xxxx", "下滑");
        } else if (concurrentFirstVisibleItem < firstVisibleItem) {
            Log.i("xxxx", "上滑");
        }

//        if (visibleItemCount + firstVisibleItem == datas.size()) {
//            Log.i("xxxx", "滑动到底部");
//        }
        Log.i("xxxx", "onScroll");

        concurrentFirstVisibleItem = firstVisibleItem;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    public ListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ListAdapter adapter) {
        this.adapter = adapter;
        listView.setAdapter(adapter);
    }

    public OnRefreshListener getRefreshListener() {
        return refreshListener;
    }

    public void setOnRefreshListener(OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public interface OnRefreshListener {
        void onPullDownRefresh();

        void onPullUpRefresh();
    }
}
