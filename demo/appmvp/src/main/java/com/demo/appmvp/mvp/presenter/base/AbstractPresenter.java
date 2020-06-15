package com.demo.appmvp.mvp.presenter.base;

import com.demo.appmvp.mvp.view.base.AbstractView;

public interface AbstractPresenter<T extends AbstractView> {

    void attachView(T view);

    void detachView();

}
