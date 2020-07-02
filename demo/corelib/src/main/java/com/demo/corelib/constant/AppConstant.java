package com.demo.corelib.constant;

/**
 * 基本常量定义类
 */
public class AppConstant {

    //================================================================================
    // 广播相关信息
    //================================================================================
    public static final String BROADCAST_ACTION_SINGLE_IMAGE_SELECT = "BROADCAST_ACTION_SINGLE_IMAGE_SELECT"; // 选择单张图片

    // 三方组件
    public static String WECHAT_APP_ID = "";   //微信appId
    public static String WECHAT_APP_SECRET = ""; //微信appSecret
    public static String QQ_APP_ID = "";   //QQ appId
    public static String QQ_APP_KEY = ""; //QQ appSecret

    // 时间格式化
    public static final String DATE_FORMAT_PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_PATTERN_MM_DD = "MM-dd";
    public static final String DATE_FORMAT_PATTERN_YYYY_MM_DD_SLASH = "yyyy/MM/dd";
    public static final String DATE_FORMAT_PATTERN_YYYY_NIAN_MM_YUE = "yyyy年M月";
    public static final String DATE_FORMAT_PATTERN_YYYY_NIAN_MM_YUE_DD_RI = "yyyy年MM月dd日";
    public static final String DATE_FORMAT_PATTERN_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_PATTERN_YYYY_MM_DD_HH_MM_SLASH = "yyyy/MM/dd HH:mm";
    public static final String DATE_FORMAT_PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    // url处理正则表达式
    public static final String URL_PRETREATMENT_REGEX = ":[a-zA-Z]+";

    // 默认经纬度（大连）
    public static double DEFAULT_LONGITUDE = 121.618622; // 默认经度
    public static double DEFAULT_LATITUDE = 38.91459; // 默认纬度

    // 列表每次请求的条数
    public static final int COMMON_PAGE_SIZE = 10;
}
