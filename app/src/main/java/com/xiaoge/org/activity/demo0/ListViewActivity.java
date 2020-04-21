package com.xiaoge.org.activity.demo0;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaoge.org.R;
import com.xiaoge.org.util.DisplayUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListViewActivity extends AppCompatActivity implements AbsListView.OnScrollListener, View.OnTouchListener {
    public static final String TAG = ListViewActivity.class.getSimpleName();

    @BindView(R.id.lv_tets)
    ListView lv_tets;

    @BindView(R.id.ll_head)
    LinearLayout ll_head;

    @BindView(R.id.tv_last_update_time)
    TextView tv_last_update_time;

    private String last_update_time = "";

    private int concurrentFirstVisibleItem;

    private Handler handler = new Handler();

    List<String> datas = new ArrayList<>();
    private MyAdapter adapter;

    private int maxMargin;
    private int threshold;

    private boolean isRefreshing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ButterKnife.bind(this);
        initView();
        initData();

        adapter = new MyAdapter();
        lv_tets.setAdapter(adapter);
        lv_tets.setOnScrollListener(this);
        lv_tets.setOnTouchListener(this);
    }

    private void initView() {
        maxMargin = DisplayUtil.dip2px(ListViewActivity.this, 80);
        threshold = maxMargin / 2;
    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            datas.add(String.valueOf(i));
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Log.i(TAG, "onScrollStateChanged scrollState: " + scrollState);
    }

    @Override
    /**
     * firstVisibleItem 第一个可见item的index
     * visibleItemCount listView中可见的条目数
     * totalItemCount  listView中item的总数
     */
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.i("xxxx", "onScroll firstVisibleItem: " + firstVisibleItem);
        Log.i("xxxx", "onScroll visibleItemCount: " + visibleItemCount);
        Log.i("xxxx", "onScroll totalItemCount: " + totalItemCount);

        if (concurrentFirstVisibleItem > firstVisibleItem) {
            Log.i("xxxx", "下滑");
        } else if (concurrentFirstVisibleItem < firstVisibleItem) {
            Log.i("xxxx", "上滑");
        }

        if (visibleItemCount + firstVisibleItem == datas.size()) {
            Log.i("xxxx", "滑动到底部");
        }

        concurrentFirstVisibleItem = firstVisibleItem;
    }

    private int start_y;
    private int last_y;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                start_y = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int) event.getY();
                int dex = y - start_y;
                if (dex > 0) {
                    if (concurrentFirstVisibleItem == 0 && dex >= threshold && !isRefreshing) {
                        isRefreshing = true;
                        showHead();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return false;
    }

    private void showHead() {
        showHeadAnimation();
    }

    private void showHeadAnimation() {
        if (!TextUtils.isEmpty(last_update_time)) {
            tv_last_update_time.setText(last_update_time);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        last_update_time = dateFormat.format(new Date());

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll_head.getLayoutParams();
        int from = params.topMargin;
        int to = 0;
        ValueAnimator animator = ValueAnimator.ofFloat(from, to);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float f = (float) animation.getAnimatedValue();
                params.topMargin = (int) f;
                ll_head.setLayoutParams(params);

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

    private void hideHeadAnimation() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll_head.getLayoutParams();
        int from = 0;
        int to = -maxMargin;
        ValueAnimator animator = ValueAnimator.ofFloat(from, to);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float f = (float) animation.getAnimatedValue();
                params.topMargin = (int) f;
                ll_head.setLayoutParams(params);

                if (f == to) {
                    isRefreshing = false;
                }
            }
        });
        animator.start();
    }

    private void refreshData() {
        Log.i("xxxx", "-------- refreshData add data --------");

        for (int i = 0; i < 5; i++) {
            datas.add("add data: " + System.currentTimeMillis());
        }
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = new TextView(ListViewActivity.this);
            tv.setText(datas.get(position));
            return tv;
        }
    }
}
