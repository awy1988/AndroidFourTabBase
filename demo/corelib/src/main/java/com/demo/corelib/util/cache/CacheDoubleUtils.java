package com.demo.corelib.util.cache;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 二级缓存（内存+磁盘）
 */
class CacheDoubleUtils implements ICacheDouble {

    private static final Map<String, CacheDoubleUtils> CACHE_MAP = new HashMap<>();

    private static final String DEFAULT_CACHE_KEY = "DEFAULT_CACHE_KEY";

    private static com.blankj.utilcode.util.CacheDoubleUtils sDefaultCacheDoubleUtils;

    private CacheDoubleUtils() {
        sDefaultCacheDoubleUtils = com.blankj.utilcode.util.CacheDoubleUtils.getInstance();
    }

    public static CacheDoubleUtils getInstance() {
        return getInstance(DEFAULT_CACHE_KEY);
    }

    public static CacheDoubleUtils getInstance(String cacheKey) {
        CacheDoubleUtils cache = CACHE_MAP.get(cacheKey);
        if (cache == null) {
            synchronized (CacheDoubleUtils.class) {
                cache = CACHE_MAP.get(cacheKey);
                if (cache == null) {
                    cache = new CacheDoubleUtils();
                    CACHE_MAP.put(cacheKey, cache);
                }
            }
        }
        return cache;
    }

    @Override
    public void put(@NonNull String key, byte[] value) {
        sDefaultCacheDoubleUtils.put(key, value);
    }

    @Override
    public void put(@NonNull String key, byte[] value, int saveTime) {
        sDefaultCacheDoubleUtils.put(key, value, saveTime);
    }

    @Override
    public byte[] getBytes(@NonNull String key) {
        return sDefaultCacheDoubleUtils.getBytes(key);
    }

    @Override
    public byte[] getBytes(@NonNull String key, byte[] defaultValue) {
        return sDefaultCacheDoubleUtils.getBytes(key, defaultValue);
    }

    @Override
    public void put(@NonNull String key, String value) {
        sDefaultCacheDoubleUtils.put(key, value);
    }

    @Override
    public void put(@NonNull String key, String value, int saveTime) {
        sDefaultCacheDoubleUtils.put(key, value, saveTime);
    }

    @Override
    public String getString(@NonNull String key) {
        return sDefaultCacheDoubleUtils.getString(key);
    }

    @Override
    public String getString(@NonNull String key, String defaultValue) {
        return sDefaultCacheDoubleUtils.getString(key, defaultValue);
    }

    @Override
    public void put(@NonNull String key, Bitmap value) {
        sDefaultCacheDoubleUtils.put(key, value);
    }

    @Override
    public void put(@NonNull String key, Bitmap value, int saveTime) {
        sDefaultCacheDoubleUtils.put(key, value, saveTime);
    }

    @Override
    public Bitmap getBitmap(@NonNull String key) {
        return sDefaultCacheDoubleUtils.getBitmap(key);
    }

    @Override
    public Bitmap getBitmap(@NonNull String key, Bitmap defaultValue) {
        return sDefaultCacheDoubleUtils.getBitmap(key, defaultValue);
    }

    @Override
    public void put(@NonNull String key, Drawable value) {
        sDefaultCacheDoubleUtils.put(key, value);
    }

    @Override
    public void put(@NonNull String key, Drawable value, int saveTime) {
        sDefaultCacheDoubleUtils.put(key, value, saveTime);
    }

    @Override
    public Drawable getDrawable(@NonNull String key) {
        return sDefaultCacheDoubleUtils.getDrawable(key);
    }

    @Override
    public Drawable getDrawable(@NonNull String key, Drawable defaultValue) {
        return sDefaultCacheDoubleUtils.getDrawable(key, defaultValue);
    }

    @Override
    public void put(@NonNull String key, Parcelable value) {
        sDefaultCacheDoubleUtils.put(key, value);
    }

    @Override
    public void put(@NonNull String key, Parcelable value, int saveTime) {
        sDefaultCacheDoubleUtils.put(key, value, saveTime);
    }

    @Override
    public <T> T getParcelable(@NonNull String key, @NonNull Parcelable.Creator<T> creator) {
        return sDefaultCacheDoubleUtils.getParcelable(key, creator);
    }

    @Override
    public <T> T getParcelable(@NonNull String key, @NonNull Parcelable.Creator<T> creator,
        T defaultValue) {
        return sDefaultCacheDoubleUtils.getParcelable(key, creator, defaultValue);
    }

    @Override
    public void put(@NonNull String key, Serializable value) {
        sDefaultCacheDoubleUtils.put(key, value);
    }

    @Override
    public void put(@NonNull String key, Serializable value, int saveTime) {
        sDefaultCacheDoubleUtils.put(key, value, saveTime);
    }

    @Override
    public Object getSerializable(@NonNull String key) {
        return sDefaultCacheDoubleUtils.getSerializable(key);
    }

    @Override
    public Object getSerializable(@NonNull String key, Object defaultValue) {
        return sDefaultCacheDoubleUtils.getSerializable(key, defaultValue);
    }

    @Override
    public long getCacheDiskSize() {
        return sDefaultCacheDoubleUtils.getCacheDiskSize();
    }

    @Override
    public void remove(@NonNull String key) {
        sDefaultCacheDoubleUtils.remove(key);
    }

    @Override
    public void clear() {
        sDefaultCacheDoubleUtils.clear();
    }
}
