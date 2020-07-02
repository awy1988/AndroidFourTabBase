package com.demo.corelib.constant;

import com.demo.corelib.BuildConfig;

/**
 * 接口常量定义类
 */
public class ApiConstant {

    public static String BASE_URL = BuildConfig.API_BASE_URL;
    public static final int DEFAULT_REQUEST_TIME_OUT_SECONDS = 30;

    public interface Sort {
        /** 升序 */
        String ASC = "asc";
        /** 降序 */
        String DESC = "desc";
    }

    /* 上传图片 */
    public static final String API_UPLOAD_USER_LOGO = "/files/user-logos";
}
