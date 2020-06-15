package com.demo.appmvp.data;

import com.demo.appmvp.data.db.AppDatabase;
import com.demo.appmvp.data.db.User;
import com.demo.appmvp.data.model.CaptchaDataModel;
import com.demo.appmvp.data.remote.api.auth.AccountService;
import com.demo.appmvp.data.remote.api.auth.AuthService;
import com.demo.corelib.model.common.ResponseModel;
import com.demo.corelib.network.base.RequestCallbackListener;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
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

    public Observable<Response<ResponseModel<Object>>> validateCaptcha(String text, String encryptedData) {
        return mAuthService.validateCaptcha(text, encryptedData);
    }

    /**
     * 获取用户信息
     * @param listener
     */
    public void getUserInfo(String token, RequestCallbackListener<User> listener) {

        Observable.just(token)
            .subscribeOn(Schedulers.io())
            .flatMap(new Function<String, ObservableSource<User>>() {
                @Override
                public ObservableSource<User> apply(String token) throws Throwable {
                    User user = mAppDatabase.userDao().getUserInfoByToken(token);
                    if (user == null) {
                        user = new User();
                    }
                    return Observable.just(user);
                }
            })
            .flatMap(new Function<User, ObservableSource<ResponseModel<User>>>() {
                @Override
                public ObservableSource<ResponseModel<User>> apply(User user) throws Throwable {
                    if (user.token == null) {
                        return mAccountService.getUserInfo();
                    }
                    ResponseModel<User> responseModel = new ResponseModel<>();
                    responseModel.setData(user);
                    return Observable.just(responseModel);
                }
            })
            .flatMap(new Function<ResponseModel<User>, ObservableSource<ResponseModel<User>>>() {
                @Override
                public ObservableSource<ResponseModel<User>> apply(ResponseModel<User> userResponseModel) throws Throwable {
                    // 缓存用户信息
                    User user = userResponseModel.getData();
                    user.token = token;
                    mAppDatabase.userDao().insertAll(user);
                    return Observable.just(userResponseModel);
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<ResponseModel<User>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull ResponseModel<User> userResponseModel) {
                    listener.onCompleted(userResponseModel.getData(), null);
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    listener.onEndedWithError(e.getMessage());
                }

                @Override
                public void onComplete() {

                }
            });

    }

}
