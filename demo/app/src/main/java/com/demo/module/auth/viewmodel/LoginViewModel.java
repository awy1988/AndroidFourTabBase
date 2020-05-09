package com.demo.module.auth.viewmodel;

import android.text.TextUtils;
import androidx.lifecycle.MutableLiveData;
import com.demo.corelib.model.common.LinksModel;
import com.demo.corelib.network.base.HandleResponseHeaderRequestCallbackListener;
import com.demo.corelib.util.SPUtils;
import com.demo.module.base.viewmodel.BaseViewModel;
import com.demo.module.data.remote.api.auth.AuthService;
import okhttp3.Headers;

public class LoginViewModel extends BaseViewModel {

    private MutableLiveData<String> mUserName = new MutableLiveData<>();
    private MutableLiveData<String> mPassword = new MutableLiveData<>();



    public MutableLiveData<String> getUserName() {
        return mUserName;
    }

    public MutableLiveData<String> getPassword() {
        return mPassword;
    }

    public void login() {
        // 所有的数据都在LoginViewModel中，所以，login的时候就不需要外界传入任何数据。

        new AuthService().authorizations(this.mUserName.getValue(), this.mPassword.getValue(),
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
                }
            });
    }



}
