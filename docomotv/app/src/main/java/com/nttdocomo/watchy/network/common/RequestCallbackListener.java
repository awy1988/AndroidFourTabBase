package com.nttdocomo.watchy.network.common;


import com.nttdocomo.watchy.model.common.LinksModel;

public interface RequestCallbackListener<T> {
    void onStarted();
    void onCompleted(T data, LinksModel links);
    void onEndedWithError(String errorInfo);
}
