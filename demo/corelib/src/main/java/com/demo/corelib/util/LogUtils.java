package com.demo.corelib.util;

import com.demo.corelib.CoreLib;
import com.orhanobut.logger.Logger;

/**
 * Log工具类
 */
public class LogUtils {
    /**
     * 是否为调试模式
     */
    public static final boolean ENABLE_LOG_DEBUG = CoreLib.IS_DEBUG;

    public static void v(final String msg) {
        if (ENABLE_LOG_DEBUG) {
            Logger.v("" + msg);
        }
    }

    public static void v(final String tag, final String msg) {
        if (ENABLE_LOG_DEBUG) {
            Logger.t(tag).v("" + msg);
        }
    }

    public static void d(final String msg) {
        if (ENABLE_LOG_DEBUG) {
            Logger.d("" + msg);
        }
    }

    public static void d(final String tag, final String msg) {
        if (ENABLE_LOG_DEBUG) {
            Logger.t(tag).d("" + msg);
        }
    }

    public static void i(final String msg) {
        Logger.i("" + msg);
    }

    public static void i(final String tag, final String msg) {
        Logger.t(tag).i("" + msg);
    }

    public static void w(final String msg) {
        Logger.w("" + msg);
    }

    public static void w(final String tag, final String msg) {
        Logger.t(tag).w("" + msg);
    }

    public static void w(final String tag, final String msg, final Throwable tr) {
        Logger.log(Logger.WARN, tag, "" + msg, tr);
    }

    public static void w(final String tag, final Throwable tr) {
        Logger.log(Logger.WARN, tag, "", tr);
    }

    public static void e(final String msg) {
        Logger.e("" + msg);
    }

    public static void e(final String tag, final String msg) {
        Logger.t(tag).e("" + msg);
    }

    public static void e(final String tag, final String msg, final Throwable tr) {
        Logger.t(tag).e(tr, "" + msg);
    }

    public static void e(final String tag, final Throwable tr) {
        Logger.t(tag).e(tr, "");
    }

    public static void json(String json) {
        if (ENABLE_LOG_DEBUG) {
            // json 格式数据，只有在Debug模式下才进行正常的输出。
            Logger.json(json);
        }
    }

    public static void xml(String xml) {
        if (ENABLE_LOG_DEBUG) {
            // xml 格式数据，只有在Debug模式下才进行正常的输出。
            Logger.xml(xml);
        }
    }
}

