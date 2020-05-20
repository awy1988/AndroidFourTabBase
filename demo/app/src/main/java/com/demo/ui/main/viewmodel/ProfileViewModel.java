package com.demo.ui.main.viewmodel;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.demo.corelib.model.common.LinksModel;
import com.demo.corelib.network.base.RequestCallbackListener;
import com.demo.corelib.util.SPUtils;
import com.demo.data.UserRepository;
import com.demo.data.db.User;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<Boolean> isDataLoading = new MutableLiveData<>();
    private MutableLiveData<User> mUser = new MutableLiveData<>();


    public MutableLiveData<User> getUser() {
        return mUser;
    }

    public void setUser(User user) {
        this.mUser.postValue(user);
    }

    private UserRepository mUserRepository;

    public ProfileViewModel(UserRepository userRepository) {
        this.mUserRepository = userRepository;
    }

    public MutableLiveData<Boolean> getIsDataLoading() {
        return this.isDataLoading;
    }

    private void setDataLoading(boolean isLoading) {
        this.isDataLoading.setValue(isLoading);
    }


    public void getUserInfo() {
        this.mUserRepository.getUserInfo(SPUtils.getAccessToken(), new RequestCallbackListener<User>() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onCompleted(User data, LinksModel links) {
                setUser(data);
                Log.e("TAG", data.toString());
            }

            @Override
            public void onEndedWithError(String errorInfo) {

            }
        });
    }
}
