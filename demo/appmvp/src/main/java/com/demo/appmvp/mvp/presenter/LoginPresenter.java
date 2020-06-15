package com.demo.appmvp.mvp.presenter;

import android.text.TextUtils;
import com.demo.appmvp.data.UserRepository;
import com.demo.appmvp.data.model.CaptchaDataModel;
import com.demo.appmvp.mvp.contract.LoginContract;
import com.demo.appmvp.network.payload.ApiException;
import com.demo.appmvp.network.payload.ResponseTransformer;
import com.demo.corelib.model.common.ResponseModel;
import com.demo.corelib.util.SPUtils;
import com.google.gson.Gson;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.io.IOException;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter {

    LoginContract.View mView;

    private UserRepository mUserRepository;

    private CompositeDisposable mCompositeDisposable;

    public LoginPresenter(UserRepository userRepository) {
        this.mUserRepository = userRepository;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(LoginContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        this.mCompositeDisposable.clear();
    }

    @Override
    public void login(String userName, String password, String inputCaptcha, String encryptedData) {

        if (TextUtils.isEmpty(inputCaptcha) || TextUtils.isEmpty(encryptedData)) {
            checkCaptchaNecessaryAndLogin(userName, password, inputCaptcha, encryptedData);
            return;
        }

        mUserRepository.validateCaptcha(inputCaptcha, encryptedData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap(new Function<ResponseModel<Object>, ObservableSource<Response<ResponseModel>>>() {
                @Override
                public ObservableSource<Response<ResponseModel>> apply(ResponseModel<Object> objectResponseModel)
                    throws Throwable {
                    if (objectResponseModel.getSuccess()) {
                        return mUserRepository.login(userName, password, inputCaptcha, encryptedData).subscribeOn(
                            Schedulers.io());
                    } else {
                        return Observable.error(new ApiException(200, objectResponseModel.getError().getMessage(),
                            objectResponseModel.getError().getMessage()));
                    }
                }
            }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Response<ResponseModel>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull Response<ResponseModel> responseModelResponse) {
                    handleLogin(responseModelResponse);
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    mView.showError(e.getMessage());
                    refreshCaptcha();
                }

                @Override
                public void onComplete() {

                }
            });
    }

    private void checkCaptchaNecessaryAndLogin(String userName, String password, String inputCaptcha,
        String encryptedData) {
        mUserRepository.captchaNecessaryCheck(userName)
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap(
                (Function<ResponseModel<CaptchaDataModel>, ObservableSource<Response<ResponseModel>>>) captchaDataModelResponseModel -> {
                    if (captchaDataModelResponseModel.getData() == null) {
                        return mUserRepository.login(userName, password, inputCaptcha, encryptedData).subscribeOn(
                            Schedulers.io());
                    }
                    mView.showCaptcha(captchaDataModelResponseModel.getData());
                    return Observable.error(new ApiException(200, "请输入验证码", "请输入验证码"));
                }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Response<ResponseModel>>() {

                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull Response<ResponseModel> response) {

                    handleLogin(response);
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    mView.showError(e.getMessage());
                }

                @Override
                public void onComplete() {

                }
            });
    }

    private void handleLogin(@NonNull Response<ResponseModel> response) {
        saveToken(response);

        if (response.errorBody() == null) {
            return;
        }

        try {
            String errorInfo = response.errorBody().string();
            if (TextUtils.isEmpty(errorInfo)) {
                mView.showError("出现错误");
                return;
            }
            Gson gson = new Gson();
            ResponseModel responseModel = gson.fromJson(errorInfo, ResponseModel.class);
            String errorMessage = "error occurred!";
            if (responseModel != null
                && responseModel.getError() != null
                && responseModel.getError().getMessage() != null) {
                errorMessage = responseModel.getError().getMessage();
            }
            mView.showError(errorMessage);
        } catch (IOException e) {
            e.printStackTrace();
            mView.showError("出现错误");
        }
    }

    private void saveToken(@NonNull Response<ResponseModel> responseModelResponse) {
        String token = responseModelResponse.headers().get("x-access-token");
        if (!TextUtils.isEmpty(token)) {
            // 存储token
            SPUtils.saveAccessToken(token);
            mView.loginSuccess();
        }
    }

    @Override
    public void refreshCaptcha() {
        Disposable disposable = mUserRepository.captchaNecessaryCheck(null)
            .compose(ResponseTransformer.handleResult())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(captchaDataModel -> {
                mView.showCaptcha(captchaDataModel);
            }, throwable -> {
                mView.showError(throwable.getMessage());
            });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void validateCaptcha() {

    }

    @Override
    public void captchaCheck(String userName) {
        mUserRepository.captchaNecessaryCheck(userName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(ResponseTransformer.handleResult());
    }
}
