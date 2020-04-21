package com.xiaoge.org.test;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.util.LinkedHashMap;

public class LruCacheTest {
    public void test() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };

        lruCache.put("hello", null);
        lruCache.get("hello");

        LinkedHashMap<String, Bitmap> linkedHashMap = new LinkedHashMap<String, Bitmap>(64, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Entry<String, Bitmap> eldest) {
                return super.removeEldestEntry(eldest);
            }
        };
    }
}
