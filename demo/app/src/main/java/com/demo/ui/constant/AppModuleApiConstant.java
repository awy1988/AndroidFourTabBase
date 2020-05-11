package com.demo.ui.constant;

import com.demo.BuildConfig;
import com.demo.corelib.constant.ApiConstant;

public class AppModuleApiConstant extends ApiConstant {

    public static String BASE_URL = BuildConfig.API_BASE_URL;
    public static boolean IS_DEBUG = BuildConfig.DEBUG;
    public static final String API_AUTHORIZATIONS = "/authorizations";
    public static final String API_CAPTCHA = "/captcha";
}
