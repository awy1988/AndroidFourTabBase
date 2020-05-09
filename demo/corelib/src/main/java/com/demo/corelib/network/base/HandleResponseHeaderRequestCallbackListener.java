package com.demo.corelib.network.base;

import okhttp3.Headers;

public interface HandleResponseHeaderRequestCallbackListener<T> extends RequestCallbackListener<T> {
    void onHandleResponseHeaders(Headers headers);
}
