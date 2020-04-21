package com.demo.model.common;

/**
 * Created by anweiyang on 18/1/3.
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
