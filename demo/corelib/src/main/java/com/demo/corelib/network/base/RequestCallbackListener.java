package com.demo.corelib.network.base;


import com.demo.corelib.model.common.LinksModel;

public interface RequestCallbackListener<T> {
    void onStarted();
    void onCompleted(T data, LinksModel links);
    void onEndedWithError(String errorInfo);
}
