package com.xiaoge.org.activity.demo0;

import android.media.ImageReader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xiaoge.org.R;
import com.xiaoge.org.views.RecordingView_x;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = TestActivity.class.getSimpleName();

    private Button btn_start;
    private RecordingView_x rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        btn_start = findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);

        rv = findViewById(R.id.rv);
        rv.setListener(new RecordingView_x.Listener() {
            @Override
            public void onFinish() {
                Log.i(TAG, "onFinish");
            }

            @Override
            public void onProgress(float p) {
                Log.i(TAG, "onProgress: " + p);
            }
        });

        ImageReader reader;
        // ImageUtils utils;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                rv.start();
                break;
            default:
                break;
        }
    }
}
