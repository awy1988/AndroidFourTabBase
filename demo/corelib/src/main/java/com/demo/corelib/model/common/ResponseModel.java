package com.demo.corelib.model.common;

/**
 * 网络请求返回的数据模型共通部分
 */

public class ResponseModel<T> {
    private Boolean success;
    private ErrorModel error;
    private LinksModel links;
    private T data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LinksModel getLinks() {
        return links;
    }

    public void setLinks(LinksModel links) {
        this.links = links;
    }
}
