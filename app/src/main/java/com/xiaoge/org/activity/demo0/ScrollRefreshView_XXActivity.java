package com.xiaoge.org.activity.demo0;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaoge.org.R;
import com.xiaoge.org.views.ScrollRefreshView_XX;

import java.util.ArrayList;
import java.util.List;

public class ScrollRefreshView_XXActivity extends AppCompatActivity {
    List<String> datas = new ArrayList<>();

    private ScrollRefreshView_XX scrollRefreshView_xx;

    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_refresh_view__xx);
        scrollRefreshView_xx = findViewById(R.id.srv_xx);
        scrollRefreshView_xx.setOnRefreshListener(new ScrollRefreshView_XX.OnRefreshListener() {
            @Override
            public void onPullDownRefresh() {
                refresh();
            }

            @Override
            public void onPullUpRefresh() {
                refresh();
            }
        });


        for (int i = 0; i < 100; i++) {
            datas.add(String.valueOf(i));
        }

        adapter = new MyAdapter();
        scrollRefreshView_xx.setAdapter(adapter);
    }

    private void refresh() {
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
            TextView tv = new TextView(ScrollRefreshView_XXActivity.this);
            tv.setText(datas.get(position));
            return tv;
        }
    }
}
