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
import com.xiaoge.org.views.VoiceLineView;

public class VoiceLineViewActivity extends AppCompatActivity implements Recorder.ReceiverCallback {
    public static final String TAG = VoiceLineViewActivity.class.getSimpleName();

    private Recorder recorder;

    private boolean isStarted = false;

    @BindView(R.id.voicLine)
    VoiceLineView voiceLineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_line_view);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_start)
    void start() {
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
        Log.i(TAG, "onReceivedVoice");
        int decibel = VoiceUtil.decibel(voices);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                voiceLineView.setVolume(decibel);
            }
        });
    }
}
