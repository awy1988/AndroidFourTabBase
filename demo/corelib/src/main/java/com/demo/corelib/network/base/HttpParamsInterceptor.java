package com.demo.corelib.network.base;

import android.text.TextUtils;
import com.blankj.utilcode.util.StringUtils;
import com.demo.corelib.CoreLib;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Http params Interceptor
 */
public class HttpParamsInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();

        // add public http header params
        Request.Builder builder = originalRequest.newBuilder();
        String userAgent = getUserAgent();
        if (!StringUtils.isEmpty(userAgent)) {
            builder.addHeader("User-Agent", userAgent);
        }

        String token = getToken();
        if (!TextUtils.isEmpty(token)) {
            builder.addHeader("Authorization", "Bearer " + token);
        }
        Request request = builder.build();

        return chain.proceed(request);
    }

    /**
     * 获取UserAgent，用于Http请求头中添加UserAgent字段
     * @return userAgent字符串
     */
    private String getUserAgent() {
        return CoreLib.httpConfig().getUserAgent();
    }

    /**
     * 获取网络请求token
     */
    private String getToken() {
        return CoreLib.httpConfig().getToken();
    }
}
