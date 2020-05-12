package com.xiaoge.org.media;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.text.TextUtils;

import com.xiaoge.org.util.LogUtil;

import java.io.File;
import java.io.RandomAccessFile;

public class APPMediaPlayer implements MediaPlayer.OnCompletionListener {
    private static final String TAG = APPMediaPlayer.class.getSimpleName();

    private MediaPlayer mediaPlayer;

    private AudioTrack audioTrack;

    private String cur_path;

    private int position = 0;

    // private volatile State state = State.INIT;

    private volatile boolean isPaused = false;

    private volatile boolean isStop = false;

    private byte[] buffer;

    private int bufferSize = 1024;

    public void play(String path) {
        if (!TextUtils.isEmpty(path) && !path.equalsIgnoreCase(cur_path)) {
            File file = new File(path);
            boolean b = file.exists();
            if (b) {
                startup(path);
            }

            if (!b) LogUtil.i(TAG, "文件不存在: " + path);
        }
    }

    private void startup(String path) {
        stop();

        cur_path = path;

        if (path.endsWith("mp3")) {
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(path);
                mediaPlayer.setOnCompletionListener(this);
                mediaPlayer.prepare();
                mediaPlayer.start();
                isStop = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            int temp_bufferSize = AudioTrack.getMinBufferSize(16000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
            if (temp_bufferSize > bufferSize) {
                bufferSize = temp_bufferSize;
            }

            buffer = new byte[bufferSize];
            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 16000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM);
            audioTrack.play();
            isPaused = false;
            isStop = false;
            fetchData(path, position);
        }
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            position = mediaPlayer.getCurrentPosition();
        } else if (audioTrack != null) {
            audioTrack.pause();
            isPaused = true;
        }
    }

    public void goOn() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(position);
            mediaPlayer.start();
        } else if (audioTrack != null) {
            audioTrack.play();
            isPaused = false;
            fetchData(cur_path, position);
        }
    }

    public void stop() {
        isStop = true;

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            position = 0;
        } else if (audioTrack != null) {
            audioTrack.stop();
            audioTrack.release();
            audioTrack = null;
            isPaused = false;
            position = 0;
        }
    }

    private void fetchData(final String path, final int p) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int len = 0;
                    RandomAccessFile file = new RandomAccessFile(path, "r");
                    file.seek(p);
                    while (!isStop && !isPaused && (len = file.read(buffer, 0, buffer.length)) > 0) {
                        position += len;
                        audioTrack.write(buffer, 0, len);
                    }

                    if (!isPaused) {
                        cur_path = null;
                    }

                    file.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static APPMediaPlayer getInstance() {
        return APPMediaPlayerHolder.instance;
    }

    private APPMediaPlayer() {
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        stop();

        cur_path = null;
    }

    private static class APPMediaPlayerHolder {
        public static APPMediaPlayer instance = new APPMediaPlayer();
    }

    private enum State {
        INIT, PLAYING
    }
}