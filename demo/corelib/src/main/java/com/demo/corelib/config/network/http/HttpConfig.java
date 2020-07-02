package com.demo.corelib.config.network.http;

public class HttpConfig {

    /**
     * 是否为debug模式
     * 目前在debug模式下会输出 OkHttp 的log。
     */
    private boolean isDebug;

    /**
     * UserAgent
     */
    private String userAgent;

    /**
     * 访问令牌
     */
    private String token;

    /**
     * Http请求超时时间（秒）
     */
    private Integer connectTimeout;

    public String getUserAgent() {
        return userAgent;
    }

    public HttpConfig setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public String getToken() {
        return token;
    }

    public HttpConfig setToken(String token) {
        this.token = token;
        return this;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public HttpConfig setDebug(boolean debug) {
        isDebug = debug;
        return this;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public HttpConfig setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

}
