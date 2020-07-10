package com.demo.corelib.util.cache;

public class CacheUtils {

    ///////////////////////////////////////////////////////////////////////////
    // memory cache 内存缓存
    ///////////////////////////////////////////////////////////////////////////

    public static ICacheMemory getCacheMemoryInstance() {
        return CacheMemoryUtils.getInstance();
    }

    public static ICacheMemory getCacheMemoryInstance(int maxSize) {
        return CacheMemoryUtils.getInstance(maxSize);
    }

    public static ICacheMemory getCacheMemoryInstance(String cacheMemoryIdentifier, int maxSize) {
        return CacheMemoryUtils.getInstance(cacheMemoryIdentifier, maxSize);
    }

    ///////////////////////////////////////////////////////////////////////////
    // disk cache 磁盘缓存
    ///////////////////////////////////////////////////////////////////////////

    public static ICacheDisk getCacheDiskInstance() {
        return CacheDiskUtils.getInstance();
    }

    public static ICacheDisk getCacheDiskInstance(String cachePath) {
        return CacheDiskUtils.getInstance(cachePath);
    }

    public static ICacheDisk getCacheDiskInstance(long maxSize) {
        return CacheDiskUtils.getInstance(maxSize);
    }

    public static ICacheDisk getCacheDiskInstance(String cachePath, long maxSize) {
        return CacheDiskUtils.getInstance(cachePath, maxSize);
    }

    ///////////////////////////////////////////////////////////////////////////
    // double cache 二级缓存
    ///////////////////////////////////////////////////////////////////////////
    public static ICacheDouble getCacheDoubleInstance() {
        return CacheDoubleUtils.getInstance();
    }

}
