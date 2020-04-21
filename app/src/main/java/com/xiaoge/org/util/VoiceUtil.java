package com.xiaoge.org.util;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class VoiceUtil {
    public static final String TAG = VoiceUtil.class.getSimpleName();

    public static int decibel(byte[] voice) {
        if (voice == null || voice.length == 0) {
            return 0;
        }

        if (voice.length % 2 != 0) {
            byte[] bytes = new byte[voice.length + 1];
            System.arraycopy(voice, 0, bytes, 0, voice.length);
            bytes[voice.length] = 0;
            voice = bytes;
        }

        short[] shorts = new short[voice.length / 2];
        ByteBuffer.wrap(voice).order(ByteOrder.nativeOrder()).asShortBuffer().get(shorts);
        long v = 0;
        int len = voice.length / 2;

        for (int i = 0; i < len; i++) {
            v += shorts[i] * shorts[i];
        }

        double mean = v / (double) len;
        double volume = 10 * Math.log10(mean);

        return (int) volume;
    }

    public static int maxDecibel(byte[] voice) {
        if (voice == null || voice.length == 0) {
            return 0;
        }
        short[] shorts = new short[voice.length / 2];
        ByteBuffer.wrap(voice).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shorts);
        int len = voice.length / 2;
        long max = 0;
        for (int i = 0; i < len; i++) {
            long tmp = shorts[i] * shorts[i];
            if (max < tmp) {
                max = tmp;
            }
        }
        double volume = 10 * Math.log10(max);

        return (int) volume;
    }
}
