package com.demo.corelib;

import android.app.Application;
import android.text.TextUtils;
import com.demo.corelib.config.log.LogConfig;
import com.demo.corelib.config.network.http.HttpConfig;
import com.demo.corelib.util.SPUtils;
import com.demo.corelib.util.Utils;

public class CoreLib {

    public static boolean IS_DEBUG;

    private static LogConfig logConfig = new LogConfig();
    private static HttpConfig httpConfig = new HttpConfig();

    /**
     * 核心模块初始化方法
     * @param application
     */
    public static void init(Application application, boolean isDebug) {
        // 这里有个问题，由于CoreLib类是静态类，是否会导致不同APP之间的互相数据干扰？这里需要搞清楚内存的管理问题。
        IS_DEBUG = isDebug;
        Utils.init(application);
        logConfig.init();
        httpConfig.setDebug(isDebug);
        String token = SPUtils.getAccessToken();
        if (!TextUtils.isEmpty(token)) {
            httpConfig.setToken(token);
        }
    }

    /**
     * log 输出配置
     */
    public static LogConfig logConfig() {
        return logConfig;
    }

    /**
     * Http配置
     */
    public static HttpConfig httpConfig() {
        return httpConfig;
    }

}
