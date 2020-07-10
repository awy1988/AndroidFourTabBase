package com.demo.corelib.util.cache;

public interface ICacheMemory {
    void put(String key, Object value);

    void put(String key, Object value, int saveTime);

    <T> T get(String key);

    <T> T get(String key, T defaultValue);

    Object remove(String key);

    void clear();
}
