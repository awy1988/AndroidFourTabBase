package com.demo.constant;

/**
 * 基本常量定义类
 *
 * @author weiyang.an
 * @version 1.0 2017/12/28
 */
public class AppConstant {

    //================================================================================
    // 广播相关信息
    //================================================================================

    public static String BROADCAST_TAB_RELOAD_ACTION = "BROADCAST_TAB_RELOAD_ACTION";
    public static String BROADCAST_APP_FINISH_ACTION = "BROADCAST_APP_FINISH_ACTION";
    public static String BROADCAST_ACTION_SHOPPING_CART_REFRESH = "BROADCAST_ACTION_SHOPPING_CART_REFRESH";
    public static String BROADCAST_ACTION_SHOPPING_CART_ITEM_COUNT_REFRESH = "BROADCAST_ACTION_SHOPPING_CART_ITEM_COUNT_REFRESH"; // 更新购物车数量圆形红色提示
    public static String BROADCAST_TAB_RELOAD_PARAM_KEY = "TAB_KEY";

    public static String BROADCAST_ACTION_PAY_SUCCESS = "BROADCAST_ACTION_PAY_SUCCESS"; // 支付成功回调广播
    public static String BROADCAST_ACTION_PAY_FAILED = "BROADCAST_ACTION_PAY_FAILED"; // 支付失败回调广播
    public static String BROADCAST_ACTION_REFRESH_ORDER_LIST = "BROADCAST_ACTION_REFRESH_ORDER_LIST"; // 刷新订单列表广播
    public static String BROADCAST_ACTION_FINISH_ORDER_DETAIL = "BROADCAST_ACTION_FINISH_ORDER_DETAIL"; // 关闭订单详情
    public static final String BROADCAST_ACTION_SINGLE_IMAGE_SELECT = "BROADCAST_ACTION_SINGLE_IMAGE_SELECT"; // 选择单张图片
    /** 登录成功 */
    public static String BROADCAST_ACTION_LOGIN_SUCCESS = "BROADCAST_ACTION_LOGIN_SUCCESS";

    /* 三方组件 */
    public static String WECHAT_APP_ID = "wx487e4d5686f0265a";   //微信appId
    public static String WECHAT_APP_SECRET = "cd2096f5787c0a0e31e8e2d3d2b624ea"; //微信appSecret
    public static String QQ_APP_ID = "1106675156";   //QQ appId
    public static String QQ_APP_KEY = "6wYN4UhoMUB97UYB"; //QQ appSecret

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

    /* 默认经纬度（大连） */
    public static double DEFAULT_LONGITUDE = 121.618622; // 默认经度
    public static double DEFAULT_LATITUDE = 38.91459; // 默认纬度

    /** 列表每次请求的条数 */
    public static final int COMMON_PAGE_SIZE = 10;
}
