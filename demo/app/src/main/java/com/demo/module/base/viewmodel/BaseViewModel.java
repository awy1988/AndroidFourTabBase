package com.demo.module.base.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {

    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();

    public MutableLiveData<Boolean> isLoading() {
        return mIsLoading;
    }

    public void setLoading(boolean isLoading) {
        this.mIsLoading.setValue(isLoading);
    }

    public void postLoading(boolean isLoading) {
        this.mIsLoading.postValue(isLoading);
    }
}
