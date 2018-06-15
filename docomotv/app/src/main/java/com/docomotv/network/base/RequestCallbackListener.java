package com.docomotv.network.base;

import com.docomotv.model.common.LinksModel;

public interface RequestCallbackListener<T> {
    void onStarted();
    void onCompleted(T data, LinksModel links);
    void onEndedWithError(String errorInfo);
}
