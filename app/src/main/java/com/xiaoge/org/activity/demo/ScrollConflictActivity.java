package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xiaoge.org.R;

import java.util.ArrayList;
import java.util.List;

public class ScrollConflictActivity extends AppCompatActivity {
    @BindView(R.id.lv_tets)
    ListView lv_tets;

    @BindView(R.id.lv_tets1)
    ListView lv_tets1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_conflict);
        ButterKnife.bind(this);

        initView();
    }

    public void initView() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add("01-" + i + "-" + System.currentTimeMillis());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(ScrollConflictActivity.this,
                android.R.layout.simple_list_item_1);
        adapter.addAll(datas);
        lv_tets.setAdapter(adapter);

        List<String> datas1 = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas1.add("02-" + i + "-" + System.currentTimeMillis());
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter(ScrollConflictActivity.this,
                android.R.layout.simple_list_item_1);
        adapter1.addAll(datas1);
        lv_tets1.setAdapter(adapter1);
    }
}
