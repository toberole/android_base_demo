package com.xiaoge.org;

import android.media.AudioRecord;
import android.media.MediaRecorder;

import com.xiaoge.org.util.AppUtil;


public class Recorder implements Runnable {
    public static final String TAG = Recorder.class.getSimpleName();

    private volatile boolean isStop = true;

    private AudioRecord mAudioRecord;

    private ReceiverCallback cb;

    public Recorder(ReceiverCallback cb) {
        this.cb = cb;
    }

    public void stop() {
        isStop = true;
    }

    public void start() {
        AppUtil.getInstance().execute(this);
    }

    @Override
    public void run() {
        try {
            int sysMinBufferSize = AudioRecord.getMinBufferSize(Constant.DEFAULT_SAMPLE_RATE, Constant.DEFAULT_CHANNEL_CONFIG, Constant.DEFAULT_ENCODE_FORMAT);
            sysMinBufferSize = sysMinBufferSize < Constant.MIN_BUFFER ? Constant.MIN_BUFFER : sysMinBufferSize;
            mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, Constant.DEFAULT_SAMPLE_RATE, Constant.DEFAULT_CHANNEL_CONFIG,
                    Constant.DEFAULT_ENCODE_FORMAT, sysMinBufferSize);
            mAudioRecord.startRecording();
            isStop = false;

            byte[] buf = new byte[sysMinBufferSize];

            if (cb != null) {
                cb.onStartup();
            }

            while (!isStop) {
                int len = mAudioRecord.read(buf, 0, buf.length);

                byte[] temp = buf;
                if (len < buf.length && len > 0) {
                    temp = new byte[len];
                    System.arraycopy(buf, 0, temp, 0, len);
                }

                if (cb != null && temp != null) {
                    cb.onReceivedVoice(temp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (mAudioRecord != null && (mAudioRecord.getState() == AudioRecord.STATE_INITIALIZED || mAudioRecord.getState() == AudioRecord.RECORDSTATE_RECORDING)) {
                    mAudioRecord.stop();
                    mAudioRecord.release();
                    mAudioRecord = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public interface ReceiverCallback {
        void onStartup();
        void onReceivedVoice(byte[] voices);
    }
}
