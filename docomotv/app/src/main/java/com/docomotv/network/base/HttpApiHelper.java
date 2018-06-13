package com.docomotv.network.base;

import android.util.Log;

import com.docomotv.constant.ApiConstant;
import com.docomotv.model.common.ResponseModel;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * network api helper
 *
 * @author weiyang.an
 * @version 1.0 2018/6/12
 */
public class HttpApiHelper {

    private static final String TAG = HttpApiHelper.class.getSimpleName();

    private static Retrofit RETROFIT_INSTANCE;

    public static Retrofit getRetrofitInstance() {

        if (RETROFIT_INSTANCE == null) {
            synchronized (HttpApiHelper.class) {
                if (RETROFIT_INSTANCE == null) {

                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.addInterceptor(new HttpParamsInterceptor())
                        .connectTimeout(ApiConstant.REQUEST_TIME_OUT_SECONDS, TimeUnit.SECONDS);

                    if (ApiConstant.IS_DEBUG) {
                       builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
                    }

                    OkHttpClient client = builder.build();

                    RETROFIT_INSTANCE = new Retrofit.Builder()
                            .baseUrl(ApiConstant.BASE_URL)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return RETROFIT_INSTANCE;
    }

    /**
     * 执行网络请求
     * @param call
     * @param listener
     * @param <T>
     */
    public static <T> void executeRequest(Call<ResponseModel<T>> call, final RequestCallbackListener<T> listener) {

        if (listener != null) {
            listener.onStarted();
        }

        if (call != null) {
            call.enqueue(new Callback<ResponseModel<T>>() {
                @Override
                public void onResponse(Call<ResponseModel<T>> call, Response<ResponseModel<T>> response) {
                    Log.d(TAG, "onResponse: ");
                    // TODO 在这里可以添加针对所有请求返回时的共通处理。
                    if (listener != null) {
                        listener.onCompleted(response.body() == null ? null : response.body().getData());
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel<T>> call, Throwable t) {
                    Log.d(TAG, "onFailure: ");
                    if (listener != null) {
                        listener.onEndedWithError(t == null ? "error occurred!" : t.getMessage());
                    }
                }
            });
        }
    }
}
