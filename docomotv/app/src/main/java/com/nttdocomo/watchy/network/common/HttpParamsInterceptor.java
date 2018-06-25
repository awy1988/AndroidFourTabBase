package com.nttdocomo.watchy.network.common;

import android.text.TextUtils;

import com.nttdocomo.watchy.module.App;
import com.nttdocomo.watchy.module.App;
import com.nttdocomo.watchy.util.SPCacheUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Http params Interceptor
 * @author weiyang.an
 * version 1.0 2018/6/11
 */
public class HttpParamsInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();

        // add public http header params
        Request.Builder builder = originalRequest.newBuilder();
        builder.addHeader("User-Agent", App.getApp().getUserAgent());

        String token = SPCacheUtils.getAccessToken();
        if (!TextUtils.isEmpty(token)) {
            builder.addHeader("Authorization", "Bearer " + token);
        }
        Request request = builder.build();

        return chain.proceed(request);
    }
}
