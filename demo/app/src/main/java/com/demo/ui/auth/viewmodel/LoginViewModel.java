package com.demo.ui.auth.viewmodel;

import android.text.TextUtils;
import androidx.lifecycle.MutableLiveData;
import com.demo.corelib.model.common.LinksModel;
import com.demo.corelib.network.base.HandleResponseHeaderRequestCallbackListener;
import com.demo.corelib.network.base.RequestCallbackListener;
import com.demo.corelib.util.SPUtils;
import com.demo.data.UserRepository;
import com.demo.data.model.CaptchaDataModel;
import com.demo.ui.base.viewmodel.BaseViewModel;
import okhttp3.Headers;

public class LoginViewModel extends BaseViewModel {

    private MutableLiveData<String> mUserName = new MutableLiveData<>();
    private MutableLiveData<String> mPassword = new MutableLiveData<>();
    private MutableLiveData<String> mInputCaptcha = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLoginSuccess = new MutableLiveData<>();
    private MutableLiveData<CaptchaDataModel> mCaptchaData = new MutableLiveData<>();

    UserRepository mUserRepository;

    private LoginCallbackListener mLoginCallbackListener;
    private ValidateCaptchaCallbackListener mValidateCaptchaCallbackListener;
    private RefreshCaptchaCallbackListener mRefreshCaptchaCallbackListener;
    private CaptchaCheckListener mCaptchaCheckListener;

    public LoginViewModel(UserRepository userRepository) {
        this.mUserRepository = userRepository;
        mLoginSuccess.setValue(false);
        mUserName.setValue("super");
        mPassword.setValue("super");
        mLoginCallbackListener = new LoginCallbackListener();
        mValidateCaptchaCallbackListener = new ValidateCaptchaCallbackListener();
        mRefreshCaptchaCallbackListener = new RefreshCaptchaCallbackListener();
        mCaptchaCheckListener = new CaptchaCheckListener();
    }

    public MutableLiveData<String> getUserName() {
        return mUserName;
    }

    public MutableLiveData<String> getPassword() {
        return mPassword;
    }

    public MutableLiveData<String> getInputCaptchaText() {
        return mInputCaptcha;
    }

    public MutableLiveData<Boolean> getLoginSuccess() {
        return mLoginSuccess;
    }

    public MutableLiveData<CaptchaDataModel> getCaptchaData() {
        return mCaptchaData;
    }

    public void login() {
        // 所有的数据都在LoginViewModel中，所以，login的时候就不需要外界传入任何数据。
        mUserRepository.login(this.mUserName.getValue(), this.mPassword.getValue(), this.mInputCaptcha.getValue(),
            getEncryptedData(),
            mLoginCallbackListener);
    }

    private String getEncryptedData() {
        if (this.mCaptchaData.getValue() != null) {
            return this.mCaptchaData.getValue().getEncryptedData();
        }
        return null;
    }

    /**
     * 验证验证码
     */
    public void validateCaptcha() {
        mUserRepository.validateCaptcha(this.mInputCaptcha.getValue(), getEncryptedData(),
            mValidateCaptchaCallbackListener);
    }

    /**
     * 刷新验证码
     */
    public void refreshCaptcha() {
        mUserRepository.captchaNecessaryCheck(null, mRefreshCaptchaCallbackListener);
    }

    /**
     * 判断是否需要验证码
     */
    public void captchaCheck() {
        mUserRepository.captchaNecessaryCheck(this.mUserName.getValue(), mCaptchaCheckListener);
    }

    /**
     * 是否需要验证码判断接口回调
     */
    public class CaptchaCheckListener implements RequestCallbackListener<CaptchaDataModel> {
        @Override
        public void onStarted() {

        }

        @Override
        public void onCompleted(CaptchaDataModel data, LinksModel links) {

            mCaptchaData.setValue(data);

            if (data == null) {
                login();
            }
        }

        @Override
        public void onEndedWithError(String errorInfo) {
            getErrorMessage().setValue(errorInfo);
        }
    }

    /**
     * 登录回调
     */
    public class LoginCallbackListener implements HandleResponseHeaderRequestCallbackListener {
        @Override
        public void onStarted() {
            setLoading(true);
        }

        @Override
        public void onHandleResponseHeaders(Headers headers) {
            String token = headers.get("x-access-token");
            if (!TextUtils.isEmpty(token)) {
                // 存储token
                SPUtils.saveAccessToken(token);
                mLoginSuccess.setValue(true);
            }
        }

        @Override
        public void onCompleted(Object data, LinksModel links) {
            setLoading(false);
        }

        @Override
        public void onEndedWithError(String s) {
            setLoading(false);
            setErrorMessage(s);
        }
    }

    /**
     * 验证验证码回调
     */
    public class ValidateCaptchaCallbackListener implements RequestCallbackListener {
        @Override
        public void onStarted() {

        }

        @Override
        public void onCompleted(Object data, LinksModel links) {
            // 验证成功
            LoginViewModel.this.login();
        }

        @Override
        public void onEndedWithError(String errorInfo) {
            getErrorMessage().setValue(errorInfo);
            refreshCaptcha();
        }
    }

    /**
     * 刷新验证码回调
     */
    public class RefreshCaptchaCallbackListener implements RequestCallbackListener<CaptchaDataModel> {
        @Override
        public void onStarted() {

        }

        @Override
        public void onCompleted(CaptchaDataModel data, LinksModel links) {
            mCaptchaData.setValue(data);
        }

        @Override
        public void onEndedWithError(String errorInfo) {
            getErrorMessage().setValue(errorInfo);
        }
    }
}
