package com.xiaoge.org.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xiaoge.org.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SegmentView extends FrameLayout implements View.OnClickListener {
    private SelectedChangedListener selectedChangedListener;

    private int selected_index = 0;
    public static int LEFT = 0;
    public static int RIGHT = 1;

    private TextView tv_left;
    private TextView tv_right;

    public SegmentView(@NonNull Context context) {
        this(context, null);
    }

    public SegmentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SegmentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

        View v = View.inflate(context, R.layout.segment_view, null);
        addView(v);
        tv_left = v.findViewById(R.id.tv_left);
        tv_right = v.findViewById(R.id.tv_right);
        tv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    private void init(Context context) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                if (selected_index == RIGHT) {
                    tv_left.setBackgroundResource(R.drawable.bg_left_item_selected);
                    tv_right.setBackgroundResource(R.drawable.bg_right_item_normal);
                    selected_index = LEFT;
                    if (null != selectedChangedListener) {
                        selectedChangedListener.OnSelectedChanged(LEFT);
                    }
                }
                break;
            case R.id.tv_right:
                if (selected_index == LEFT) {
                    tv_left.setBackgroundResource(R.drawable.bg_left_item_normal);
                    tv_right.setBackgroundResource(R.drawable.bg_right_item_selected);
                    selected_index = RIGHT;
                    if (null != selectedChangedListener) {
                        selectedChangedListener.OnSelectedChanged(RIGHT);
                    }
                }
                break;
        }
    }

    public void setSelectedChangedListener(SelectedChangedListener selectedChangedListener) {
        this.selectedChangedListener = selectedChangedListener;
    }

    public interface SelectedChangedListener {
        void OnSelectedChanged(int index);
    }
}
