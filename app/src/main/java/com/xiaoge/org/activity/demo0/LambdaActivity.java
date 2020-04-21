package com.xiaoge.org.activity.demo0;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import com.xiaoge.org.R;


public class LambdaActivity extends AppCompatActivity {
    public static final String TAG = LambdaActivity.class.getSimpleName();

    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.btn_2)
    Button btn2;

    private LinearLayout.LayoutParams btn1_param;
    private int topMargin;

    @OnClick(R.id.btn_2)
    void btn2Clicked() {
        topMargin += 5;
        btn1_param.topMargin = topMargin;
        btn1.setLayoutParams(btn1_param);
        // btn1.requestLayout();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lambda);
        ButterKnife.bind(this);

        btn1_param = (LinearLayout.LayoutParams) btn1.getLayoutParams();
        topMargin = btn1_param.topMargin;
        Log.i(TAG, "btn1_param.topMargin: " + topMargin);
    }
}
