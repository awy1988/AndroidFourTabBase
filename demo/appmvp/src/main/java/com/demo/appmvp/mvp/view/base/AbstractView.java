package com.demo.appmvp.mvp.view.base;

public interface AbstractView {
    void showLoading();
    void hideLoading();
    void showError(String errorMsg);
}
