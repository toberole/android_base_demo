package com.cat.zeus.utils;

import java.lang.ref.SoftReference;
import java.util.LinkedList;

public class BytesPool extends LinkedList<SoftReference<byte[]>> {

    private int maxPoolSize = 32;

    private int bytesBufferSize = 1024;

    public void init(int bytesBufferSize, int maxPoolSize) {
        this.bytesBufferSize = bytesBufferSize;
        this.maxPoolSize = maxPoolSize;
    }

    public synchronized byte[] obtain() {
        byte[] buffer = null;
        if (size() != 0) {
            SoftReference<byte[]> buf = null;
            while (size() - 1 >= 0 && (buf = remove()) != null) {
                buffer = buf.get();
                if (buffer != null) break;
            }
        }

        if (buffer == null) {
            buffer = new byte[bytesBufferSize];
        }

        return buffer;
    }

    public synchronized void putBack(byte[] buf) {
        if (buf != null && buf.length == bytesBufferSize && size() < maxPoolSize) {
            SoftReference<byte[]> buffer = new SoftReference<>(buf);
            add(buffer);
        }
    }

    public synchronized void clearPool() {
        super.clear();
    }

    private static class ByteArrayPoolHolder {
        public static BytesPool instance = new BytesPool();
    }

    private BytesPool() {

    }

    public static BytesPool getInstance() {
        return ByteArrayPoolHolder.instance;
    }
}
