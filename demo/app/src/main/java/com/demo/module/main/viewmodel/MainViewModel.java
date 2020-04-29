package com.demo.module.main.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.demo.module.data.MainRepository;

public class MainViewModel extends ViewModel {
    private MutableLiveData<String> title = new MutableLiveData<>();

    private final MainRepository mMainRepository;

    public MainViewModel() {
        this.mMainRepository = MainRepository.getInstance();
    }

    public MutableLiveData<String> getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title.setValue(title);
    }
}
