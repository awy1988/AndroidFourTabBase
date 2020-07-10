package com.demo.corelib.util.cache;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import java.io.Serializable;

public interface ICacheDouble {

    ///////////////////////////////////////////////////////////////////////////
    // about bytes
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put bytes in cache.
     *
     * @param key The key of cache.
     * @param value The value of cache.
     */
    void put(@NonNull final String key, final byte[] value);

    /**
     * Put bytes in cache.
     *
     * @param key The key of cache.
     * @param value The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    void put(@NonNull final String key, byte[] value, final int saveTime);

    /**
     * Return the bytes in cache.
     *
     * @param key The key of cache.
     * @return the bytes if cache exists or null otherwise
     */
    byte[] getBytes(@NonNull final String key);

    /**
     * Return the bytes in cache.
     *
     * @param key The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the bytes if cache exists or defaultValue otherwise
     */
    byte[] getBytes(@NonNull final String key, final byte[] defaultValue);

    ///////////////////////////////////////////////////////////////////////////
    // about String
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put string value in cache.
     *
     * @param key The key of cache.
     * @param value The value of cache.
     */
    void put(@NonNull final String key, final String value);

    /**
     * Put string value in cache.
     *
     * @param key The key of cache.
     * @param value The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    void put(@NonNull final String key, final String value, final int saveTime);

    /**
     * Return the string value in cache.
     *
     * @param key The key of cache.
     * @return the string value if cache exists or null otherwise
     */
    String getString(@NonNull final String key);
    /**
     * Return the string value in cache.
     *
     * @param key The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the string value if cache exists or defaultValue otherwise
     */
    String getString(@NonNull final String key, final String defaultValue);

    ///////////////////////////////////////////////////////////////////////////
    // Bitmap cache
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put bitmap in cache.
     *
     * @param key The key of cache.
     * @param value The value of cache.
     */
    void put(@NonNull final String key, final Bitmap value);
    /**
     * Put bitmap in cache.
     *
     * @param key The key of cache.
     * @param value The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    void put(@NonNull final String key, final Bitmap value, final int saveTime);

    /**
     * Return the bitmap in cache.
     *
     * @param key The key of cache.
     * @return the bitmap if cache exists or null otherwise
     */
    Bitmap getBitmap(@NonNull final String key);
    /**
     * Return the bitmap in cache.
     *
     * @param key The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the bitmap if cache exists or defaultValue otherwise
     */
    Bitmap getBitmap(@NonNull final String key, final Bitmap defaultValue);

    ///////////////////////////////////////////////////////////////////////////
    // about Drawable
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put drawable in cache.
     *
     * @param key The key of cache.
     * @param value The value of cache.
     */
    void put(@NonNull final String key, final Drawable value);

    /**
     * Put drawable in cache.
     *
     * @param key The key of cache.
     * @param value The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    void put(@NonNull final String key, final Drawable value, final int saveTime);

    /**
     * Return the drawable in cache.
     *
     * @param key The key of cache.
     * @return the drawable if cache exists or null otherwise
     */
    Drawable getDrawable(@NonNull final String key);
    /**
     * Return the drawable in cache.
     *
     * @param key The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the drawable if cache exists or defaultValue otherwise
     */
    Drawable getDrawable(@NonNull final String key, final Drawable defaultValue);

    ///////////////////////////////////////////////////////////////////////////
    // about Parcelable
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put parcelable in cache.
     *
     * @param key The key of cache.
     * @param value The value of cache.
     */
    void put(@NonNull final String key, final Parcelable value);

    /**
     * Put parcelable in cache.
     *
     * @param key The key of cache.
     * @param value The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    void put(@NonNull final String key, final Parcelable value, final int saveTime);

    /**
     * Return the parcelable in cache.
     *
     * @param key The key of cache.
     * @param creator The creator.
     * @param <T> The value type.
     * @return the parcelable if cache exists or null otherwise
     */
    <T> T getParcelable(@NonNull final String key, @NonNull final Parcelable.Creator<T> creator);

    /**
     * Return the parcelable in cache.
     *
     * @param key The key of cache.
     * @param creator The creator.
     * @param defaultValue The default value if the cache doesn't exist.
     * @param <T> The value type.
     * @return the parcelable if cache exists or defaultValue otherwise
     */
    <T> T getParcelable(@NonNull final String key, @NonNull final Parcelable.Creator<T> creator, final T defaultValue);

    ///////////////////////////////////////////////////////////////////////////
    // about Serializable
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put serializable in cache.
     *
     * @param key The key of cache.
     * @param value The value of cache.
     */
    void put(@NonNull final String key, final Serializable value);

    /**
     * Put serializable in cache.
     *
     * @param key The key of cache.
     * @param value The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    void put(@NonNull final String key, final Serializable value, final int saveTime);

    /**
     * Return the serializable in cache.
     *
     * @param key The key of cache.
     * @return the bitmap if cache exists or null otherwise
     */
    Object getSerializable(@NonNull final String key);

    /**
     * Return the serializable in cache.
     *
     * @param key The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the bitmap if cache exists or defaultValue otherwise
     */
    Object getSerializable(@NonNull final String key, final Object defaultValue);

    /**
     * Return the size of cache in disk.
     *
     * @return the size of cache in disk
     */
    long getCacheDiskSize();

    /**
     * Remove the cache by key.
     *
     * @param key The key of cache.
     */
    void remove(@NonNull String key);

    /**
     * Clear all of the cache.
     */
    void clear();
}
