package com.xiaoge.org.activity.demo0;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.widget.TextView;

import com.xiaoge.org.R;

public class SegmentViewActivity extends AppCompatActivity {
    @BindView(R.id.tv_left)
    TextView tv_left;

    @BindView(R.id.tv_right)
    TextView tv_right;

    private int selected_index = 0;
    private int LEFT = 0;
    private int RIGHT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segment_view);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_left)
    void tv_left_clicked() {
        if (selected_index == RIGHT) {
            // left 被选中
            tv_left.setBackgroundResource(R.drawable.bg_left_item_selected);
            tv_right.setBackgroundResource(R.drawable.bg_right_item_normal);
            selected_index = LEFT;
        }
    }

    @OnClick(R.id.tv_right)
    void tv_left_right() {
        if (selected_index == LEFT) {
            // right 被选中
            tv_left.setBackgroundResource(R.drawable.bg_left_item_normal);
            tv_right.setBackgroundResource(R.drawable.bg_right_item_selected);
            selected_index = RIGHT;
        }
    }
}
