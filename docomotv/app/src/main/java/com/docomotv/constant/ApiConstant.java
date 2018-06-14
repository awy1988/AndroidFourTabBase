package com.docomotv.constant;

/**
 * 接口常量定义类
 *
 * @author weiyang.an
 * @version 1.0 2018/6/11
 */
public class ApiConstant {

    private static final _ACCESS_POINT ACCESS_POINT = _ACCESS_POINT.RELEASE;
    public static String BASE_UA_APP_NAME = "docomotv";
    public static String BASE_URL = getSaasHost();
    public static final int REQUEST_TIME_OUT_SECONDS = 30;
//    public static final boolean IS_DEBUG = getDebugFlag();
    public static final boolean IS_DEBUG = true;


    /**
     * ACCESS POINT:通信地址
     * RELEASE : 商用环境（共同）
     * STAGING : 测试环境（共同）
     * LOCAL   : 开发者各自环境（个人）
     */
    private static enum _ACCESS_POINT {
        RELEASE,
        STAGING,
        LOCAL
    }

    /**
     * 获取家服务的主机IP或域名
     *
     * @return String 主机IP或域名
     */
    public static String getSaasHost() {
        switch (ACCESS_POINT) {
            case RELEASE:
                return "https://api.proding.net";
//                return "https://www.yahoo.co.jp";
            case STAGING:
                return "http://39.106.49.60:3001";
            case LOCAL:
                return "http://192.168.11.254:3400";
            default:
                return "http://192.168.11.4:3400";
        }
    }

    /**
     * get the flag that indicates whether the app is in debug mode or not
     *
     * @return true-is debug , false- is release
     */
    public static boolean getDebugFlag() {

        return ACCESS_POINT != _ACCESS_POINT.RELEASE;
    }



    public interface Sort {
        /** 升序 */
        String ASC = "asc";
        /** 降序 */
        String DESC = "desc";
    }

    /* 上传图片 */
    public static final String API_UPLOAD_USER_LOGO = "/files/user-logos";

    /* 商品业务 */
    public static final String API_ITEMS = "/items"; // 查询商品

    /* 用户账户 */
    public static final String API_AUTHORIZATIONS = "/authorizations"; // 用户登录
    public static final String API_USER_PROFILE_PASSWORD = "/user/profile/password"; // 更新登录密码
    public static final String API_USER_PROFILE = "/user/profile"; // 更新登录用户资料

}
