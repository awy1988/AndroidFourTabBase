package com.demo.corelib;

import android.app.Application;
import com.demo.corelib.util.Utils;

public class CoreLib {

    /**
     * 核心模块初始化方法
     * @param application
     */
    public static void init(Application application) {
        Utils.init(application);
    }

}
