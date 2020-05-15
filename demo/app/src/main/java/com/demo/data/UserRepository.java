package com.demo.data;

import com.demo.corelib.network.base.HandleResponseHeaderRequestCallbackListener;
import com.demo.corelib.network.base.RequestCallbackListener;
import com.demo.data.database.User;
import com.demo.data.remote.api.auth.AccountService;
import com.demo.data.remote.api.auth.AuthService;
import com.demo.data.remote.api.auth.model.CaptchaDataModel;
import javax.inject.Inject;

public class UserRepository {

    private final AuthService mAuthService;
    private final AccountService mAccountService;

    // 数据来源分为两部分，一部分是本地数据库，一部分是网络
    @Inject
    public UserRepository(AuthService authService, AccountService accountService) {
        this.mAuthService = authService;
        this.mAccountService = accountService;
    }

    /**
     * 用户登录
     */
    public void login(String userName, String password, String captchaText, String encryptedData,
        HandleResponseHeaderRequestCallbackListener listener) {
        mAuthService.authorizations(userName, password, captchaText, encryptedData, listener);
    }

    public void captchaNecessaryCheck(String credential, RequestCallbackListener<CaptchaDataModel> listener) {
        mAuthService.captcha(credential, listener);
    }

    public void validateCaptcha(String text, String encryptedData, RequestCallbackListener listener) {
        mAuthService.validateCaptcha(text, encryptedData, listener);
    }

    /**
     * 获取用户信息
     * @param listener
     */
    public void getUserInfo(RequestCallbackListener<User> listener) {
        mAccountService.getUserInfo(listener);
    }

    // TODO 增加取得用户信息的逻辑，并将其缓存到数据库
}
