package com.demo.corelib.network.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import com.demo.corelib.util.SPUtils;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Http params Interceptor
 */
public class HttpParamsInterceptor implements Interceptor {

    private Context mContext;

    public HttpParamsInterceptor (Context context) {
        this.mContext = context;
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
    private String getUserAgent() {
        return "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36";
        //return ApiConstant.BASE_UA_APP_NAME +"/"+ getVersion() + " "+ getDeviceUserAgent();
    }

    /**
     * 获取当前版本
     * */
    private String getVersion() {
        try {
            PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
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
