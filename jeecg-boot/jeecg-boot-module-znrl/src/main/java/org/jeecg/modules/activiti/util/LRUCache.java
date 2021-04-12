package org.jeecg.modules.activiti.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/13.
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private int maxEntries;

    public LRUCache(int maxEntries){
        super(16, 0.75f, true);
        this.maxEntries = maxEntries;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxEntries;
    }
}
