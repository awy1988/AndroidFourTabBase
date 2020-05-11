package com.demo.data;

import com.demo.corelib.network.base.HandleResponseHeaderRequestCallbackListener;
import com.demo.corelib.network.base.RequestCallbackListener;
import com.demo.data.remote.api.auth.AuthService;
import com.demo.data.remote.api.auth.model.CaptchaDataModel;

public class UserRepository {

    private static UserRepository INSTANCE = null;

    private AuthService mAuthService;

    // 数据来源分为两部分，一部分是本地数据库，一部分是网络
    private UserRepository() {
        mAuthService = new AuthService();
    }

    /**
     * 获取数据仓库示例
     */
    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (UserRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserRepository();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 用户登录
     * @param userName
     * @param password
     */
    public void login(String userName, String password, HandleResponseHeaderRequestCallbackListener listener) {
        new AuthService().authorizations(userName, password, listener);
    }

    public void captchaCheck(String credential, RequestCallbackListener<CaptchaDataModel> listener) {
        new AuthService().captcha(credential, listener);
    }



}
