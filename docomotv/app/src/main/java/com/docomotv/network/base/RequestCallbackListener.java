package com.docomotv.network.base;

public interface RequestCallbackListener<T> {
    void onStarted();
    void onCompleted(T data);
    void onEndedWithError(String errorInfo);
}
