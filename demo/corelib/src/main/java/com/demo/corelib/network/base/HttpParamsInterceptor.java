package com.demo.corelib.network.base;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.text.TextUtils;

import com.demo.corelib.constant.ApiConstant;
import com.demo.corelib.utils.SPUtils;

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

    private Application mApp;

    public HttpParamsInterceptor (Application application) {
        this.mApp = application;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();

        // add public http header params
        Request.Builder builder = originalRequest.newBuilder();
        builder.addHeader("User-Agent", getUserAgent());

        String token = SPUtils.getAccessToken();
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
    public String getUserAgent() {
        return ApiConstant.BASE_UA_APP_NAME +"/"+ getVersion() + " "+ getDeviceUserAgent();
    }

    /**
     * 获取当前版本
     * */
    public String getVersion() {
        try {
            PackageInfo pInfo = mApp.getPackageManager().getPackageInfo(mApp.getPackageName(), 0);
            return pInfo.versionName;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取设备User-Agent的后半部分
     * @return
     */
    private String getDeviceUserAgent(){
        String defaultUserAgent = System.getProperty("http.agent");
        String[] uaSplit = defaultUserAgent.split("\\(");
        return "(" + uaSplit[1];
    }
}
