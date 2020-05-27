package com.demo.corelib.network.base;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import com.demo.corelib.constant.ApiConstant;
import com.demo.corelib.model.api.HttpQueryParamBaseModel;
import com.demo.corelib.model.common.LinksModel;
import com.demo.corelib.model.common.ResponseModel;
import com.google.gson.Gson;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * network api helper
 */
public class HttpApiHelper {

    private static final String TAG = HttpApiHelper.class.getSimpleName();

    private static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    private static final HashMap<String, Retrofit> SYSTEM_RETROFIT_INSTANCE_MAP = new HashMap<>();

    private static Application sApp;

    public static void init(Application application) {
        sApp = application;
    }

    public static Retrofit getRetrofitInstance(String baseUrl, boolean isDebug) {

        Retrofit retrofitInstance = SYSTEM_RETROFIT_INSTANCE_MAP.get(baseUrl);

        if (retrofitInstance == null) {
            synchronized (HttpApiHelper.class) {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.addInterceptor(new HttpParamsInterceptor())
                    .connectTimeout(ApiConstant.REQUEST_TIME_OUT_SECONDS, TimeUnit.SECONDS);

                if (isDebug) {
                    builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
                }

                OkHttpClient client = builder.build();

                retrofitInstance = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

                SYSTEM_RETROFIT_INSTANCE_MAP.put(baseUrl, retrofitInstance);
            }
        }

        return retrofitInstance;
    }

    /** 根据文件名获取MIME类型 */
    public static MediaType guessMimeType(String fileName) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        fileName = fileName.replace("#", "");   //解决文件名中含有#号异常的问题
        String contentType = fileNameMap.getContentTypeFor(fileName);
        if (contentType == null) {
            return MEDIA_TYPE_STREAM;
        }
        return MediaType.parse(contentType);
    }

    /**
     * 执行网络请求
     */
    public static <T> void executeRequest(Call<ResponseModel<T>> call, final RequestCallbackListener<T> listener) {

        if (listener != null) {
            listener.onStarted();
        }

        if (call == null) {
            return;
        }

        call.enqueue(new Callback<ResponseModel<T>>() {
            @Override
            public void onResponse(Call<ResponseModel<T>> call, Response<ResponseModel<T>> response) {
                Log.d(TAG, "onResponse: ");
                if (listener == null) {
                    return;
                }

                handleResponseHeaders(response);

                if (handleResponseBody(response)) {
                    return;
                }

                handleResponseError(response);
            }

            @Override
            public void onFailure(Call<ResponseModel<T>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                if (listener != null) {
                    listener.onEndedWithError(t == null ? "error occurred!" : t.getMessage());
                }
            }

            private void handleResponseHeaders(Response<ResponseModel<T>> response) {
                if (listener instanceof HandleResponseHeaderRequestCallbackListener) {
                    ((HandleResponseHeaderRequestCallbackListener) listener).onHandleResponseHeaders(
                        response.headers());
                }
            }

            private boolean handleResponseBody(Response<ResponseModel<T>> response) {
                if (response.body() != null) {
                    // 返回正确数据
                    T data = response.body().getData();
                    LinksModel links = response.body().getLinks();
                    listener.onCompleted(data, links);
                    return true;
                }
                return false;
            }

            private void handleResponseError(Response<ResponseModel<T>> response) {
                if (response.errorBody() == null) {
                    return;
                }
                // 异常处理，responseCode > 400 时回调接口的 onEndedWithError
                try {
                    String errorInfo = response.errorBody().string();
                    if (TextUtils.isEmpty(errorInfo)) {
                        listener.onEndedWithError("error occurred!");
                        return;
                    }
                    Gson gson = new Gson();
                    ResponseModel responseModel = gson.fromJson(errorInfo, ResponseModel.class);
                    String errorMessage = "error occurred!";
                    if (responseModel != null
                        && responseModel.getError() != null
                        && responseModel.getError().getMessage() != null) {
                        errorMessage = responseModel.getError().getMessage();
                    }
                    listener.onEndedWithError(errorMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onEndedWithError("error occurred!");
                }
            }

        });
    }

    /**
     * 获取请求的查询参数
     */
    public static Map<String, String> handleQueryParams(Map<String, Object> params) {

        if (params == null || params.isEmpty()) {
            return null;
        }

        Map<String, String> newQueryParams = new HashMap<>();

        // 处理输入的 get 请求查询参数
        for (Map.Entry<String, Object> entry : params.entrySet()) {

            if (entry.getValue() instanceof List) {
                // 数组
                StringBuilder sbKey = new StringBuilder(entry.getKey());
                sbKey.append("[]");
                List<String> values = (List) entry.getValue();

                if (values != null && !values.isEmpty()) {
                    for (String value : values) {
                        newQueryParams.put(sbKey.toString(), value);
                    }
                }
            } else if (entry.getValue() instanceof HttpQueryParamBaseModel) {

                Class clazz = entry.getValue().getClass();

                Field[] fields = clazz.getDeclaredFields();

                for (Field field : fields) {
                    field.setAccessible(true);
                    StringBuilder sbKey = new StringBuilder(entry.getKey());
                    sbKey.append("[")
                        .append(field.getName())
                        .append("]");
                    try {
                        Object value = field.get(entry.getValue());
                        if (value != null) {
                            newQueryParams.put(sbKey.toString(), String.valueOf(value));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                // 普通字符串
                newQueryParams.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }

        return newQueryParams;
    }
}
