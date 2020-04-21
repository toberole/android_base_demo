package com.xiaoge.org.test;

import android.util.ArrayMap;
import android.util.SparseArray;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Test21 {
    public static void main(String[] args) {
        Map<String, String> map = new LinkedHashMap<String, String>(4, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Entry<String, String> eldest) {
                System.out.println("eldest k: " + eldest.getKey() + " v: " + eldest.getValue());
                return super.removeEldestEntry(eldest);
            }
        };

        map.put("1", "1");
        map.put("2", "2");
        map.get("1");
        map.put("3", "3");
        map.put("4", "4");
        map.put("5", "5");

        SparseArray sparseArray = new SparseArray();
        sparseArray.append(1, "");
        sparseArray.delete(1);
        sparseArray.get(1);
        sparseArray.put(1, "");
        sparseArray.remove(1);

        ArrayMap arrayMap;

        Map<Integer, String> map1 = new HashMap<Integer, String>();

        LinkedList<String> strs = new LinkedList<>();
        strs.add("hello");


        Map o = new HashMap<>();
        Map o1 = new Hashtable();
        o1.put("", "");
        o.put("", "");
        Set ss = new HashSet();
        ss.add("");
    }
}
