package com.demo.corelib;

import android.app.Application;
import com.demo.corelib.config.logger.LoggerConfig;
import com.demo.corelib.util.Utils;

public class CoreLib {

    public static boolean IS_DEBUG;

    private static LoggerConfig loggerConfig = new LoggerConfig();

    /**
     * 核心模块初始化方法
     * @param application
     */
    public static void init(Application application, boolean isDebug) {
        IS_DEBUG = isDebug;
        Utils.init(application);
        logInit();
    }

    /**
     * log 输出配置
     */
    public static LoggerConfig loggerConfig() {
        return loggerConfig;
    }

    private static void logInit() {
        loggerConfig.init();
    }

}
