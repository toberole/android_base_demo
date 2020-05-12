package com.xiaoge.org.media;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.text.TextUtils;

import com.xiaoge.org.util.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import androidx.annotation.NonNull;

public class AppAudioRecorder implements Runnable {
    public static final String TAG = AppAudioRecorder.class.getSimpleName();

    private int pkgId = 0;

    private volatile boolean isStoped = false;
    public volatile boolean isCanceled = false;

    public int buffer_size = 320 * 4;

    public int sampleRateInHz = 16 * 1000;
    public int channelConfig = AudioFormat.CHANNEL_IN_MONO;
    public int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

    private AudioRecord mAudioRecord;

    private AudioReceiverCallback cb;

    private String source_voice_path;
    private FileInputStream fileInputStream;

    private long fileLength;
    private int totalLength;

    public AppAudioRecorder(AudioReceiverCallback cb, int buffer_size, int sampleRateInHz, int channelConfig, int audioFormat) {
        this.cb = cb;
        this.buffer_size = buffer_size;
        this.sampleRateInHz = sampleRateInHz;
        this.channelConfig = channelConfig;
        this.audioFormat = audioFormat;
    }

    public AppAudioRecorder(String source_voice_path, int buffer_size, @NonNull AudioReceiverCallback cb) {
        LogUtil.i(TAG, "AppAudioRecorder: " + source_voice_path);

        this.cb = cb;
        this.source_voice_path = source_voice_path;
        if (buffer_size > this.buffer_size) {
            this.buffer_size = buffer_size;
        }
    }

    public void stop() {
        isStoped = true;
    }

    public void cancel() {
        isCanceled = true;
    }

    public boolean isEnd() {
        return fileLength == totalLength;
    }

    public synchronized void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(source_voice_path)) {
            captureVoiceWithAudioRecorder();
        } else {
            captureVoiceWithFile();
        }
    }

    private void captureVoiceWithAudioRecorder() {
        LogUtil.i(TAG, "captureVoiceWithAudioRecorder");
        try {
            short[] buf;
            synchronized (AppAudioRecorder.this) {
                int sysMinBufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
                sysMinBufferSize = sysMinBufferSize < buffer_size ? buffer_size : sysMinBufferSize;
                mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRateInHz, channelConfig,
                        audioFormat, sysMinBufferSize);

                mAudioRecord.startRecording();
                int audioSessionId = mAudioRecord.getAudioSessionId();

                isStoped = false;
                buf = new short[sysMinBufferSize];
                if (cb != null) {
                    cb.onStarted(audioSessionId);
                }
            }

            int len;
            while (!isStoped && !isCanceled) {
                synchronized (AppAudioRecorder.this) {
                    len = mAudioRecord.read(buf, 0, buf.length);
                    short[] temp = buf;
                    if (len < buf.length && len > 0) {
                        temp = new short[len];
                        System.arraycopy(buf, 0, temp, 0, len);
                    }

                    if (cb != null && temp != null) {
                        cb.onCaptureVoice(temp, pkgId);
                        pkgId++;
                    }
                }
            }

            if (!isCanceled) {
                if (cb != null) {
                    short[] temp = new short[buffer_size];
                    cb.onCaptureVoice(temp, -pkgId);
                }
            } else {
//                short[] temp = new short[0];
//                cb.onCaptureVoice(temp, -Integer.MAX_VALUE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (cb != null) {
                cb.onRecorderError(-1, e.getMessage());
            }
        } finally {
            try {
                if (mAudioRecord != null) {
                    int state = mAudioRecord.getState();
                    LogUtil.i(TAG, "state: " + state);
                    mAudioRecord.stop();
                    mAudioRecord.release();
                    mAudioRecord = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void captureVoiceWithFile() {
        LogUtil.i(TAG, "captureVoiceWithFile: " + source_voice_path);

        fileLength = 0;
        try {
            byte[] buf;
            synchronized (AppAudioRecorder.this) {
                File file = new File(source_voice_path);
                fileLength = file.length();

                if (!file.exists()) {
                    cb.onRecorderError(-1, "audio_file_not_found_error_default_msg");
                    return;
                }

                fileInputStream = new FileInputStream(file);
                isStoped = false;
                buf = new byte[buffer_size * 2];
                if (cb != null) {
                    cb.onStarted(-Integer.MAX_VALUE);
                }
            }

            totalLength = 0;
            int len;
            while (!isStoped && !isCanceled) {
                synchronized (AppAudioRecorder.this) {
                    len = fileInputStream.read(buf, 0, buf.length);
                    LogUtil.i(TAG, "len: " + len);
                    if (len > 0) {
                        totalLength += len;

                        byte[] temp = buf;
                        if (len < buf.length && len > 0) {
                            temp = new byte[len];
                            System.arraycopy(buf, 0, temp, 0, len);
                        }

                        if (cb != null && temp != null) {
                            short[] vs = byte2Short(temp);
                            cb.onCaptureVoice(vs, pkgId);
                            pkgId++;
                        }
                    } else {
                        break;
                    }
                }
            }


            if (!isCanceled) {
                if (cb != null) {
                    short[] temp = new short[buffer_size];
                    cb.onCaptureVoice(temp, -pkgId);
                    cb.onExternalVoiceEnd();
                }
            } else {
//                short[] temp = new short[0];
//                cb.onCaptureVoice(temp, -Integer.MAX_VALUE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (cb != null) {
                cb.onRecorderError(-1, e.getMessage());
            }
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                    fileInputStream = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private short[] byte2Short(byte[] temp) {
        byte[] bs = temp;
        int length = bs.length;
        if (length % 2 != 0) {
            bs = new byte[temp.length + 1];
            System.arraycopy(temp, 0, bs, 0, length);
        }
        short[] shortData = new short[bs.length / 2];
        ByteBuffer.wrap(bs).order(ByteOrder.nativeOrder()).asShortBuffer().get(shortData);

        return shortData;
    }

    public interface AudioReceiverCallback {
        void onRecorderError(int errorCode, String errorMsg);

        void onStarted(int audioSessionId);

        void onCaptureVoice(short[] voices, int pkgId);

        void onExternalVoiceEnd();
    }
}
