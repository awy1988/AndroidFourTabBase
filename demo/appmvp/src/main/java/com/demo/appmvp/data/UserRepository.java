package com.demo.appmvp.data;

import com.demo.appmvp.data.db.AppDatabase;
import com.demo.appmvp.data.db.User;
import com.demo.appmvp.data.model.CaptchaDataModel;
import com.demo.appmvp.data.remote.api.auth.AccountService;
import com.demo.appmvp.data.remote.api.auth.AuthService;
import com.demo.appmvp.ui.App;
import com.demo.corelib.model.common.LinksModel;
import com.demo.corelib.model.common.ResponseModel;
import com.demo.corelib.network.base.RequestCallbackListener;
import com.demo.corelib.util.SPUtils;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;

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
     * @return
     */
    public Observable<Response<ResponseModel>> login(String userName, String password, String captchaText, String encryptedData) {
        return mAuthService.authorizations(userName, password, captchaText, encryptedData);
    }

    public Observable<ResponseModel<CaptchaDataModel>> captchaNecessaryCheck(String credential) {
        return mAuthService.captcha(credential);
    }

    public Observable<ResponseModel<Object>> validateCaptcha(String text, String encryptedData) {
        return mAuthService.validateCaptcha(text, encryptedData);
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
