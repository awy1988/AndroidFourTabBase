package com.docomotv.constant;

/**
 * 接口常量定义类
 *
 * @author weiyang.an
 * @version 1.0 2018/6/11
 */
public class ApiConstant {

    private static final _ACCESS_POINT ACCESS_POINT = _ACCESS_POINT.STAGING;
    public static String BASE_UA_APP_NAME = "docomotv";
    public static String BASE_URL = getSaasHost();


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
                return "https://www.yahoo.co.jp";
            case STAGING:
                return "http://39.106.49.60:3001";
            case LOCAL:
                return "http://192.168.11.254:3400";
            default:
                return "http://192.168.11.4:3400";
        }
    }


    public interface Sort {
        /** 升序 */
        String ASC = "asc";
        /** 降序 */
        String DESC = "desc";
    }

}
