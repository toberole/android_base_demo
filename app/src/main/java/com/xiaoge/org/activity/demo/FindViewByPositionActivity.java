package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaoge.org.R;

/**
 * 通过触摸点找到对应的View
 */
public class FindViewByPositionActivity extends AppCompatActivity {

    private TextView mTvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_view_by_position);

        mTvInfo = findViewById(R.id.tv_info);

        mTvInfo.hasOnClickListeners();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        showWidgetInfo(event.getX(), event.getY());
        return super.onTouchEvent(event);
    }

    private void showWidgetInfo(float x, float y) {
        View view = getViewByPosition(getWindow().getDecorView(), (int) x, (int) y);
        if (view != null) {
            mTvInfo.setText(view.toString());
        } else {
            mTvInfo.setText("没找到匹配的View");
        }
    }

    private View getViewByPosition(View view, int x, int y) {
        if (view == null) {
            return null;
        }

        int[] location = new int[2];
        view.getLocationInWindow(location);

        int left = location[0];
        int top = location[1];
        int right = left + view.getWidth();
        int bottom = top + view.getHeight();

        if (view instanceof ViewGroup) {
            //当前是ViewGroup容器
            int childCount = ((ViewGroup) view).getChildCount();
            //深度优先， 从最后一个子节点开始遍历，如果找到则返回。 先递归判断子View
            if (childCount > 0) {
                for (int i = childCount - 1; i >= 0; i--) {
                    View topView = getViewByPosition(((ViewGroup) view).getChildAt(i), x, y);
                    if (topView != null) {
                        return topView;
                    }
                }
            }

            //子View都没找到匹配的， 再判断自己
            if (left < x && top < y && right > x && bottom > y) {
                return view;   //当前ViewGroup就是顶层View
            } else {
                return null; //没找到匹配的
            }
        } else { //当前是View
            if (left < x && top < y && right > x && bottom > y) {
                return view;   //当前ViewGroup就是顶层View
            } else {
                return null; //没找到匹配的
            }
        }
    }
}
