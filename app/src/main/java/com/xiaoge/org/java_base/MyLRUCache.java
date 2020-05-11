package com.xiaoge.org.java_base;

import com.xiaoge.org.base_test.Sizer;
import com.xiaoge.org.util.LogUtil;

import java.util.LinkedHashMap;

import androidx.annotation.Nullable;

/**
 * LinkedHashMap = HashMap + "LinkedList"[双向链表结构 保存元素的顺序，注意双向链表较单向链表其删除效率更高，双向链表不用遍历寻找被删除节点的前驱节点]
 * 其中HashMap用于存储数据,"LinkedList"用于存储数据顺序
 */
public class MyLRUCache<K, V extends Sizer> extends LinkedHashMap<K, V> {
    private volatile int max_size;

    private volatile int cur_size;

    public MyLRUCache(int max_size) {
        super(16, 0.75f, true);
        this.max_size = max_size;
    }

    @Nullable
    @Override
    public synchronized V put(K key, V value) {
        cur_size += value.getSize();
        return super.put(key, value);
    }

    @Nullable
    @Override
    public synchronized V remove(@Nullable Object key) {
        V v = super.remove(key);
        if (null != v) {
            cur_size -= v.getSize();
        }
        return v;
    }

    @Override
    protected boolean removeEldestEntry(Entry<K, V> eldest) {
        LogUtil.i("cache-xxx", "before removeEldestEntry eldest k: " + eldest.getKey() + ",v: " + eldest.getValue());
        boolean b = cur_size > max_size;
        if (b) {
            LogUtil.i("cache-xxx", "removeEldestEntry eldest k: " + eldest.getKey() + ",v: " + eldest.getValue());
        }

        return b;
    }
}
