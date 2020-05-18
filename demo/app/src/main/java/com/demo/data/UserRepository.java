package com.demo.data;

import com.demo.corelib.model.common.LinksModel;
import com.demo.corelib.network.base.HandleResponseHeaderRequestCallbackListener;
import com.demo.corelib.network.base.RequestCallbackListener;
import com.demo.corelib.util.SPUtils;
import com.demo.data.database.AppDatabase;
import com.demo.data.database.User;
import com.demo.data.remote.api.auth.AccountService;
import com.demo.data.remote.api.auth.AuthService;
import com.demo.data.remote.api.auth.model.CaptchaDataModel;
import com.demo.ui.App;

public class UserRepository {

    private final AuthService mAuthService;
    private final AccountService mAccountService;
    private final AppDatabase mAppDatabase;
    // 数据来源分为两部分，一部分是本地数据库，一部分是网络
    public UserRepository(AuthService authService, AccountService accountService, AppDatabase appDatabase) {
        this.mAuthService = authService;
        this.mAccountService = accountService;
        this.mAppDatabase = appDatabase;
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
    public void getUserInfo(String token, RequestCallbackListener<User> listener) {

        App.getApp().getAppExecutors().diskIO().execute(() -> {
            User user = mAppDatabase.userDao().getUserInfoByToken(token);

            if (user != null) {
                listener.onCompleted(user, null);
                return;
            }

            mAccountService.getUserInfo(new RequestCallbackListener<User>() {
                @Override
                public void onStarted() {
                    listener.onStarted();
                }

                @Override
                public void onCompleted(User data, LinksModel links) {
                    // 缓存用户信息
                    App.getApp().getAppExecutors().diskIO().execute(() -> {

                        // 数据库中已经存在用户信息
                        User user1 = mAppDatabase.userDao().getUserInfoById(data.id);
                        if (user1 != null) {
                            user1.token = SPUtils.getAccessToken();
                            mAppDatabase.userDao().updateUsers(user1);
                            return;
                        }

                        // 数据库中不存在用户信息
                        data.token = SPUtils.getAccessToken();
                        mAppDatabase.userDao().insertAll(data);
                    });
                    listener.onCompleted(data, links);
                }

                @Override
                public void onEndedWithError(String errorInfo) {
                    listener.onEndedWithError(errorInfo);
                }
            });
        });



    }

}
