package net.peerindex.challenge.webcrawler;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MemoryKVStore implements KeyValueStore {

    private final Lock lock = new ReentrantLock();
    private final Map<String, String> underlying = Maps.newHashMap();

    @Override
    public boolean contains(String key) {
        lock.lock();
        try {
            return underlying.containsKey(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String get(String key) {
        lock.lock();
        try {
            return underlying.get(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean put(String key, String value) {
        lock.lock();
        try {
            final String prev = underlying.put(key, value);
            return prev == null;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean delete(String key) {
        lock.lock();
        try {
            final String prev = underlying.remove(key);
            return prev == null;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public long size() {
        return underlying.size();
    }
}
