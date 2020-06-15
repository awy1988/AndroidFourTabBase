package com.demo.appmvp.mvp.contract;

import com.demo.appmvp.data.model.CaptchaDataModel;
import com.demo.appmvp.mvp.presenter.base.AbstractPresenter;
import com.demo.appmvp.mvp.view.base.AbstractView;

public interface LoginContract {
    interface Model {
    }

    interface View extends AbstractView {
        void showCaptcha(CaptchaDataModel captchaDataModel);
        void loginSuccess();
    }

    interface Presenter extends AbstractPresenter<LoginContract.View> {
        /**
         * 登录
         * @param userName
         * @param password
         */
        void login(String userName, String password, String inputCaptcha, String encryptedData);

        /**
         * 刷新验证码
         */
        void refreshCaptcha();

        /**
         * 验证验证码
         */
        void validateCaptcha();

        /**
         * 判断是否需要验证码
         */
        void captchaCheck(String userName);


    }
}
