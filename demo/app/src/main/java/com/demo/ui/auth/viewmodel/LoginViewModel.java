package com.demo.ui.auth.viewmodel;

import android.text.TextUtils;
import androidx.lifecycle.MutableLiveData;
import com.demo.corelib.model.common.LinksModel;
import com.demo.corelib.network.base.HandleResponseHeaderRequestCallbackListener;
import com.demo.corelib.network.base.RequestCallbackListener;
import com.demo.corelib.util.SPUtils;
import com.demo.data.UserRepository;
import com.demo.data.remote.api.auth.model.CaptchaDataModel;
import com.demo.ui.base.viewmodel.BaseViewModel;
import okhttp3.Headers;

public class LoginViewModel extends BaseViewModel {

    private MutableLiveData<String> mUserName = new MutableLiveData<>();
    private MutableLiveData<String> mPassword = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLoginSuccess = new MutableLiveData<>();
    private MutableLiveData<CaptchaDataModel> mCaptchaData = new MutableLiveData<>();

    private UserRepository mUserRepository;

    public LoginViewModel() {
        mLoginSuccess.setValue(false);
    }

    public MutableLiveData<String> getUserName() {
        return mUserName;
    }

    public MutableLiveData<String> getPassword() {
        return mPassword;
    }

    public MutableLiveData<Boolean> getLoginSuccess() {
        return mLoginSuccess;
    }

    public MutableLiveData<CaptchaDataModel> getCaptchaData() {
        return mCaptchaData;
    }

    public void login() {
        // 所有的数据都在LoginViewModel中，所以，login的时候就不需要外界传入任何数据。
        // TODO 将UserRepository 改为依赖注入形式
        UserRepository.getInstance().login(this.mUserName.getValue(), this.mPassword.getValue(),
            new HandleResponseHeaderRequestCallbackListener() {

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
            });
    }

    public void captchaCheck() {
        UserRepository.getInstance().captchaCheck(this.mUserName.getValue(),
            new RequestCallbackListener<CaptchaDataModel>() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onCompleted(CaptchaDataModel data, LinksModel links) {

                }

                @Override
                public void onEndedWithError(String errorInfo) {

                }
            });
    }
}
