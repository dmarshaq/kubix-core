package org.dmarshaq.kubix.core.util;

import java.util.*;

public class IndexedHashMap<K, V> {
    private final Map<K, Integer> map;
    private final List<V> list;

    public IndexedHashMap() {
        this.map = new HashMap<>();
        this.list = new ArrayList<>();
    }

    public void put(K key, V value) {
        map.put(key, list.size());
        list.add(value);
    }

    public V get(K key) {
        return list.get(map.get(key));
    }

    public V get(int index) {
        if (index >= list.size()) {
            return null;
        }
        return list.get(index);
    }
    public int getIndex(K key) {
        return map.get(key);
    }

}
