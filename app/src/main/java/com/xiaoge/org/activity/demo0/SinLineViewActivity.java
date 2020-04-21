package com.xiaoge.org.activity.demo0;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.util.Log;

import com.xiaoge.org.R;
import com.xiaoge.org.Recorder;
import com.xiaoge.org.util.VoiceUtil;
import com.xiaoge.org.views.SinLineView;

public class SinLineViewActivity extends AppCompatActivity implements Recorder.ReceiverCallback {
    public static final String TAG = SinLineViewActivity.class.getSimpleName();

    @BindView(R.id.sin)
    SinLineView sin;

    private boolean isStarted = false;
    private Recorder recorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sin_line_view);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_record)
    void record() {
        Log.i(TAG, "start");
        if (!isStarted) {
            recorder = new Recorder(this);
            recorder.start();
        } else {
            recorder.stop();
        }
        isStarted = !isStarted;
    }

    @Override
    public void onStartup() {

    }

    @Override
    public void onReceivedVoice(byte[] voices) {
        int decibel = VoiceUtil.decibel(voices);
        Log.i(TAG, "onReceivedVoice decibel：" + decibel);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sin.setVolume(decibel);
            }
        });
    }
}
