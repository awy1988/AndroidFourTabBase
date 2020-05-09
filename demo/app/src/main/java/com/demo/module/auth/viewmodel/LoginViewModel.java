package com.demo.module.auth.viewmodel;

import androidx.lifecycle.MutableLiveData;
import com.demo.corelib.model.common.LinksModel;
import com.demo.corelib.model.item.Item;
import com.demo.corelib.network.base.RequestCallbackListener;
import com.demo.module.base.viewmodel.BaseViewModel;
import com.demo.module.data.remote.api.auth.AuthService;
import java.util.List;

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
            new RequestCallbackListener<List<Item>>() {
                @Override
                public void onStarted() {
                    setLoading(true);
                }

                @Override
                public void onCompleted(List<Item> items, LinksModel linksModel) {
                    setLoading(false);
                }

                @Override
                public void onEndedWithError(String s) {
                    setLoading(false);
                }
            });
    }



}
