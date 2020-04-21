package com.xiaoge.org.activity.rxjava_activitys;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;

import com.xiaoge.org.R;

public class RxJavaActivity_XX extends AppCompatActivity {
    @OnClick(R.id.btn1)
    void btn1() {
        test1();
    }

    private void test1() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java__xx);
        ButterKnife.bind(this);
    }
}
