package com.demo.appmvp.constant;

import com.demo.appmvp.BuildConfig;
import com.demo.corelib.constant.ApiConstant;

public class AppMvpModuleApiConstant extends ApiConstant {

    public static String BASE_URL = BuildConfig.API_BASE_URL;
    public static boolean IS_DEBUG = BuildConfig.DEBUG;
    public static final String API_AUTHORIZATIONS = "/authorizations";
    public static final String API_CAPTCHA = "/captcha";
    public static final String API_VALIDATE_CAPTCHA = "/validate-captcha";
}
