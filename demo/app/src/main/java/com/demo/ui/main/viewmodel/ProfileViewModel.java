package com.demo.ui.main.viewmodel;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.demo.corelib.model.common.LinksModel;
import com.demo.corelib.network.base.RequestCallbackListener;
import com.demo.data.UserRepository;
import com.demo.data.database.User;
import com.demo.di.component.DaggerApplicationGraph;
import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<Boolean> isDataLoading = new MutableLiveData<>();
    private MutableLiveData<User> mUser = new MutableLiveData<>();


    public MutableLiveData<User> getUser() {
        return mUser;
    }

    public void setUser(User user) {
        this.mUser.setValue(user);
    }

    @Inject
    UserRepository mUserRepository;

    public ProfileViewModel() {
        DaggerApplicationGraph.create().inject(this);
    }

    public MutableLiveData<Boolean> getIsDataLoading() {
        return this.isDataLoading;
    }

    private void setDataLoading(boolean isLoading) {
        this.isDataLoading.setValue(isLoading);
    }


    public void getUserInfo() {
        this.mUserRepository.getUserInfo(new RequestCallbackListener<User>() {
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
