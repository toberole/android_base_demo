package com.xiaoge.org.activity.demo0;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoge.org.R;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ButterknifeActivity extends AppCompatActivity {
    public static final String TAG = ButterknifeActivity.class.getSimpleName();

    @BindView(R.id.tv1)
    public TextView tv1;

    @OnClick(R.id.btn_xxxx)
    void test() {
        Toast.makeText(ButterknifeActivity.this, "test onclick", Toast.LENGTH_SHORT).show();
    }

    @BindString(R.string.test_butterknife)
    String txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butterknife);

        ButterKnife.bind(this);

        tv1.setText("ButterKnife tv1");

        Log.i(TAG, "ButterknifeActivity txt: " + txt);
    }
}
