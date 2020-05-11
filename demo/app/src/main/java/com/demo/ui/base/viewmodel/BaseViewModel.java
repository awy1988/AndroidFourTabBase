package com.demo.ui.base.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {

    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private MutableLiveData<String> mErrorMessage = new MutableLiveData<>();

    public MutableLiveData<Boolean> isLoading() {
        return mIsLoading;
    }

    public void setLoading(boolean isLoading) {
        this.mIsLoading.setValue(isLoading);
    }

    public void postLoading(boolean isLoading) {
        this.mIsLoading.postValue(isLoading);
    }

    public MutableLiveData<String> getErrorMessage() {
        return mErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.mErrorMessage.setValue(errorMessage);
    }

    public void postErrorMessage(String errorMessage) {
        this.mErrorMessage.postValue(errorMessage);
    }
}
