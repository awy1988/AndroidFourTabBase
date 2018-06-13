package com.docomotv.network.base;

import android.text.TextUtils;

import com.docomotv.module.App;
import com.docomotv.util.SPUtils;

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

        String token = SPUtils.getAccessToken();
        if (!TextUtils.isEmpty(token)) {
            builder.addHeader("Authorization", "Bearer " + token);
        }
        Request request = builder.build();

        return chain.proceed(request);
    }
}
