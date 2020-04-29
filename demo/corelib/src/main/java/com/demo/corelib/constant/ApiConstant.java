package com.demo.corelib.constant;

import com.demo.corelib.BuildConfig;

/**
 * 接口常量定义类
 */
public class ApiConstant {

    public static String BASE_UA_APP_NAME = "demo";
    public static String BASE_URL = BuildConfig.API_BASE_URL;
    public static final int REQUEST_TIME_OUT_SECONDS = 30;
    public static final boolean IS_DEBUG = BuildConfig.DEBUG;

    public interface Sort {
        /** 升序 */
        String ASC = "asc";
        /** 降序 */
        String DESC = "desc";
    }

    /* 上传图片 */
    public static final String API_UPLOAD_USER_LOGO = "/files/user-logos";

    /* 商品业务 */
    public static final String API_ITEMS = BuildConfig.API_BASE_URL_PATH + "/items"; // 查询商品

    /* 用户账户 */
    public static final String API_AUTHORIZATIONS = BuildConfig.API_BASE_URL_PATH + "/authorizations"; // 用户登录
    public static final String API_USER_PROFILE_PASSWORD = "/user/profile/password"; // 更新登录密码
    public static final String API_USER_PROFILE = "/user/profile"; // 更新登录用户资料

}
