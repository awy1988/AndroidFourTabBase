package com.demo.corelib.util.cache;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 磁盘数据缓存
 */
class CacheDiskUtils implements ICacheDisk {

    /** 磁盘缓存默认路径 */
    private static final String DEFAULT_DISK_CACHE_PATH_NAME = "diskCache";
    /** 磁盘缓存默认大小 */
    private static final long DEFAULT_DISK_CACHE_MAXSIZE = 250 * 1024 * 1024;

    private static com.blankj.utilcode.util.CacheDiskUtils sDefaultCacheDiskUtils;

    private static final Map<String, CacheDiskUtils> CACHE_MAP = new HashMap<>();

    private CacheDiskUtils(String cachePath, final long maxSize) {
        if (StringUtils.isSpace(cachePath)) {
            cachePath = DEFAULT_DISK_CACHE_PATH_NAME;
        }
        File file = new File(Utils.getApp().getCacheDir(), cachePath);
        sDefaultCacheDiskUtils = com.blankj.utilcode.util.CacheDiskUtils.getInstance(file, maxSize, Integer.MAX_VALUE);
    }

    static CacheDiskUtils getInstance() {
        return getInstance(DEFAULT_DISK_CACHE_PATH_NAME, DEFAULT_DISK_CACHE_MAXSIZE);
    }

    static CacheDiskUtils getInstance(String cachePath) {
        return getInstance(cachePath, DEFAULT_DISK_CACHE_MAXSIZE);
    }

    static CacheDiskUtils getInstance(final long maxSize) {
        return getInstance(DEFAULT_DISK_CACHE_PATH_NAME, maxSize);
    }

    static CacheDiskUtils getInstance(String cachePath, final long maxSize) {
        final String cacheKey = cachePath + "_" + maxSize;
        CacheDiskUtils cache = CACHE_MAP.get(cacheKey);
        if (cache == null) {
            synchronized (CacheDiskUtils.class) {
                cache = CACHE_MAP.get(cacheKey);
                if (cache == null) {
                    cache = new CacheDiskUtils(cachePath, maxSize);
                    CACHE_MAP.put(cacheKey, cache);
                }
            }
        }
        return cache;
    }

    @Override
    public void put(@NonNull String key, byte[] value) {
        sDefaultCacheDiskUtils.put(key, value);
    }

    @Override
    public void put(@NonNull String key, byte[] value, int saveTime) {
        sDefaultCacheDiskUtils.put(key, value, saveTime);
    }

    @Override
    public byte[] getBytes(@NonNull String key) {
        return sDefaultCacheDiskUtils.getBytes(key);
    }

    @Override
    public byte[] getBytes(@NonNull String key, byte[] defaultValue) {
        return sDefaultCacheDiskUtils.getBytes(key, defaultValue);
    }

    @Override
    public void put(@NonNull String key, String value) {
        sDefaultCacheDiskUtils.put(key, value);
    }

    @Override
    public void put(@NonNull String key, String value, int saveTime) {
        sDefaultCacheDiskUtils.put(key, value, saveTime);
    }

    @Override
    public String getString(@NonNull String key) {
        return sDefaultCacheDiskUtils.getString(key);
    }

    @Override
    public String getString(@NonNull String key, String defaultValue) {
        return sDefaultCacheDiskUtils.getString(key, defaultValue);
    }

    @Override
    public void put(@NonNull String key, Parcelable value) {
        sDefaultCacheDiskUtils.put(key, value);
    }

    @Override
    public void put(@NonNull String key, Parcelable value, int saveTime) {
        sDefaultCacheDiskUtils.put(key, value, saveTime);
    }

    @Override
    public <T> T getParcelable(@NonNull String key, @NonNull Parcelable.Creator<T> creator) {
        return sDefaultCacheDiskUtils.getParcelable(key, creator);
    }

    @Override
    public <T> T getParcelable(@NonNull String key, @NonNull Parcelable.Creator<T> creator, T defaultValue) {
        return sDefaultCacheDiskUtils.getParcelable(key, creator, defaultValue);
    }

    @Override
    public void put(@NonNull String key, Serializable value) {
        sDefaultCacheDiskUtils.put(key, value);
    }

    @Override
    public void put(@NonNull String key, Serializable value, int saveTime) {
        sDefaultCacheDiskUtils.put(key, value, saveTime);
    }

    @Override
    public Object getSerializable(@NonNull String key) {
        return sDefaultCacheDiskUtils.getSerializable(key);
    }

    @Override
    public Object getSerializable(@NonNull String key, Object defaultValue) {
        return sDefaultCacheDiskUtils.getSerializable(key, defaultValue);
    }

    @Override
    public void put(@NonNull String key, Bitmap value) {
        sDefaultCacheDiskUtils.put(key, value);
    }

    @Override
    public void put(@NonNull String key, Bitmap value, int saveTime) {
        sDefaultCacheDiskUtils.put(key, value, saveTime);
    }

    @Override
    public Bitmap getBitmap(@NonNull String key) {
        return sDefaultCacheDiskUtils.getBitmap(key);
    }

    @Override
    public Bitmap getBitmap(@NonNull String key, Bitmap defaultValue) {
        return sDefaultCacheDiskUtils.getBitmap(key, defaultValue);
    }

    @Override
    public void put(@NonNull String key, Drawable value) {
        sDefaultCacheDiskUtils.put(key, value);
    }

    @Override
    public void put(@NonNull String key, Drawable value, int saveTime) {
        sDefaultCacheDiskUtils.put(key, value, saveTime);
    }

    @Override
    public Drawable getDrawable(@NonNull String key) {
        return sDefaultCacheDiskUtils.getDrawable(key);
    }

    @Override
    public Drawable getDrawable(@NonNull String key, Drawable defaultValue) {
        return sDefaultCacheDiskUtils.getDrawable(key, defaultValue);
    }

    @Override
    public long getCacheSize() {
        return sDefaultCacheDiskUtils.getCacheSize();
    }

    @Override
    public boolean remove(String key) {
        return sDefaultCacheDiskUtils.remove(key);
    }

    @Override
    public boolean clear() {
        return sDefaultCacheDiskUtils.clear();
    }
}
