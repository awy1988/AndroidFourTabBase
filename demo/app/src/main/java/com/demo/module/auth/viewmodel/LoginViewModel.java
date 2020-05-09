package com.demo.module.auth.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

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


    }



}
