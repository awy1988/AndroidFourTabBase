package com.demo.corelib;

import android.app.Application;

import com.demo.corelib.network.base.HttpApiHelper;
import com.demo.corelib.utils.SPUtils;

public class CoreLib {

    /**
     * 核心模块初始化方法
     * @param application
     */
    public static void init(Application application) {
        SPUtils.init(application);
        HttpApiHelper.init(application);
    }

}
