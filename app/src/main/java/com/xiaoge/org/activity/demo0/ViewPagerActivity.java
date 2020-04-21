package com.xiaoge.org.activity.demo0;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaoge.org.R;
import com.xiaoge.org.views.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {

    private VerticalViewPager viewPager;
    private MyAdapter adapter;
    private String[] ts = new String[]{
            "今天天气", "上午好", "下班吧"
    };

    private List<View> views = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        viewPager = findViewById(R.id.vp);

        for (int i = 0; i < ts.length; i++) {
            View v = View.inflate(ViewPagerActivity.this, R.layout.vertical_viewpager_item, null);
            TextView tv_text = v.findViewById(R.id.tv_text);
            tv_text.setText(ts[i]);
            views.add(v);
        }

        adapter = new MyAdapter();
        viewPager.setAdapter(adapter);
    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(views.get(position));
        }
    }

    private static class MyPageTransformer implements ViewPager.PageTransformer {
        @Override
        public void transformPage(@NonNull View view, float v) {

        }
    }
}
