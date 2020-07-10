package com.demo.corelib.util.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * 内存数据缓存
 */
class CacheMemoryUtils implements ICacheMemory {

    /** 默认缓存标识符 */
    private static final String DEFAULT_CACHE_IDENTIFIER = "DEFAULT_CACHE_IDENTIFIER";

    /** 默认缓存大小 */
    private static final int DEFAULT_CACHE_MAXSIZE = 5 * 1024 * 1024;

    private static final Map<String, CacheMemoryUtils> CACHE_MAP = new HashMap<>();

    private static com.blankj.utilcode.util.CacheMemoryUtils sDefaultCacheMemoryUtils;

    /**
     * 内存数据缓存工具类对象
     * @param cacheIdentifier 内存缓存对象标识符
     * @param maxSize 缓存大小
     */
    private CacheMemoryUtils(String cacheIdentifier, final int maxSize) {
        sDefaultCacheMemoryUtils = com.blankj.utilcode.util.CacheMemoryUtils.getInstance(cacheIdentifier, maxSize);
    }

    static CacheMemoryUtils getInstance() {
        return getInstance(DEFAULT_CACHE_MAXSIZE);
    }

    static CacheMemoryUtils getInstance(final int maxSize) {
        return getInstance(DEFAULT_CACHE_IDENTIFIER, maxSize);
    }

    static CacheMemoryUtils getInstance(String cacheIdentifier, final int maxSize) {
        CacheMemoryUtils cache = CACHE_MAP.get(cacheIdentifier);
        if (cache == null) {
            synchronized (CacheMemoryUtils.class) {
                cache = CACHE_MAP.get(cacheIdentifier);
                if (cache == null) {
                    cache = new CacheMemoryUtils(cacheIdentifier, maxSize);
                    CACHE_MAP.put(cacheIdentifier, cache);
                }
            }
        }
        return cache;
    }

    @Override
    public void put(String key, Object value) {
        sDefaultCacheMemoryUtils.put(key, value);
    }

    @Override
    public void put(String key, Object value, int saveTime) {
        sDefaultCacheMemoryUtils.put(key, value, saveTime);
    }

    @Override
    public <T> T get(String key) {
        return sDefaultCacheMemoryUtils.get(key);
    }

    @Override
    public <T> T get(String key, T defaultValue) {
        return sDefaultCacheMemoryUtils.get(key, defaultValue);
    }

    @Override
    public Object remove(String key) {
        return sDefaultCacheMemoryUtils.remove(key);
    }

    @Override
    public void clear() {
        sDefaultCacheMemoryUtils.clear();
    }

}
