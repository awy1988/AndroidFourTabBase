package com.docomo.watchy.network.common;


import com.docomo.watchy.model.common.LinksModel;

public interface RequestCallbackListener<T> {
    void onStarted();
    void onCompleted(T data, LinksModel links);
    void onEndedWithError(String errorInfo);
}
