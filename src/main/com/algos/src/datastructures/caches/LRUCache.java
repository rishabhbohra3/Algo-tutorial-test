package com.thealgorithms.datastructures.caches;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {

    private final Map<K, Entry<K, V>> data = new HashMap<>();
    private Entry<K, V> head;
    private Entry<K, V> tail;
    private int cap;
    private static final int DEFAULT_CAP = 100;

    public LRUCache() {
        setCapacity(DEFAULT_CAP);
    }

    public LRUCache(int cap) {
        setCapacity(cap);
    }

    private void setCapacity(int newCapacity) {
        checkCapacity(newCapacity);
        this.cap = newCapacity;
        
    }

    private Entry<K, V> evict() {
        if (head == null) {
            throw new RuntimeException("Cache cannot be empty!");
        }
        Entry<K, V> evicted = head;
        head = evicted.getNextEntry();
        if (head != null) {
            head.setPreEntry(null);
        }
        evicted.setNextEntry(null);
        
        return evicted;
    }

    private void checkCapacity(int capacity) {
        if (capacity <= 0) {
            throw new RuntimeException("Capacity must be greater than 0!");
        }
    }

    public V get(K key) {
        if (!data.containsKey(key)) {
            return null;
        }
        final Entry<K, V> entry = data.get(key);
        moveNodeToLast(entry);
        return entry.getValue();
    }

    private void moveNodeToLast(Entry<K, V> entry) {
        if (tail == entry) {
            return;
        }
        final Entry<K, V> preEntry = entry.getPreEntry();
        final Entry<K, V> nextEntry = entry.getNextEntry();
        if (preEntry != null) {
            preEntry.setNextEntry(nextEntry);
        }
        if (nextEntry != null) {
            nextEntry.setPreEntry(preEntry);
        }
        if (head == entry) {
            head = nextEntry;
        }
        
        tail.setNextEntry(entry);
        entry.setPreEntry(tail);
        entry.setNextEntry(null);
        tail = entry;
    }

    public void put(K key, V value) {
        if (data.containsKey(key)) {
            final Entry<K, V> existingEntry = data.get(key);
            moveNodeToLast(existingEntry);
            existingEntry.setValue(value); 
            return;
        }
        Entry<K, V> newEntry = new Entry<>(); 
        if (data.size() >= cap) {
            newEntry = evict();
            data.remove(newEntry.getKey());
        }
        newEntry.setKey(key);
        newEntry.setValue(value);
        addNewEntry(newEntry);
        data.put(key, newEntry);
    }

    private void addNewEntry(Entry<K, V> newEntry) {
        if (data.isEmpty()) {
            head = newEntry;
            tail = newEntry;
            return;
        }
        tail.setNextEntry(newEntry);
        newEntry.setPreEntry(tail);
        newEntry.setNextEntry(null);
        tail = newEntry;
    }

    static final class Entry<I, J> {
        private Entry<I, J> preEntry;
        private Entry<I, J> nextEntry;
        private I key;
        private J value;

        public Entry() {
            
        }
    }
