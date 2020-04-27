package com.demo.corelib.utils;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


/**
 * key-value（键值对）存储类
 */
public class SPUtils {

    private static final String TAG = SPUtils.class.getSimpleName();

    private static final String SP_IS_FIRST_START = "first_start";
    private static final String SP_API_TOKEN = "api_token";
    private static final String SP_USER_INFO = "user_info";
    private static Application sApp;

    public static void init(Application application) {
        sApp = application;
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static void put(String key, Object object) {

        SharedPreferences sp = getSharedPreferences();
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.apply();

    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(String key, Object defaultObject) {
        SharedPreferences sp = getSharedPreferences();

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return defaultObject;
    }

    /**
     * 判断是否第一次启动
     *
     * @return boolean (true:第一次启动, false:第二次启动以后)
     */
    public static boolean isFirstStartup() {
        return (boolean) get(SP_IS_FIRST_START, true);
    }

    /**
     * 设定第一次启动
     */
    public static void setFirstStartup() {
        put(SP_IS_FIRST_START, false);
    }

    //================================================================================
    // 用户相关信息
    //================================================================================
    // 获取用户令牌
    public static String getAccessToken() {
        return (String) get(SP_API_TOKEN, "");
    }

    // 保存用户令牌
    public static void saveAccessToken(String token) {
        put(SP_API_TOKEN, token);
    }

    /**
     * 清除用户令牌
     */
    public static void clearAccessToken() {
        remove(SP_API_TOKEN);
    }


    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    private static void remove(String key) {
        SharedPreferences sp = getSharedPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key).apply();
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        SharedPreferences sp = getSharedPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().apply();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        SharedPreferences sp = getSharedPreferences();
        return sp.contains(key);
    }



    private static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(sApp);
    }


}
