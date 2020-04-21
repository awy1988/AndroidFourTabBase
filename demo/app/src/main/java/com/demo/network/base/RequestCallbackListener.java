package com.demo.network.base;

import com.demo.model.common.LinksModel;

public interface RequestCallbackListener<T> {
    void onStarted();
    void onCompleted(T data, LinksModel links);
    void onEndedWithError(String errorInfo);
}
