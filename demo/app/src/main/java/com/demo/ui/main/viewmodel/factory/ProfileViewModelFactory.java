package com.demo.ui.main.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.demo.data.UserRepository;
import com.demo.ui.main.viewmodel.ProfileViewModel;

public class ProfileViewModelFactory implements ViewModelProvider.Factory {

    private UserRepository mUserRepository;

    public ProfileViewModelFactory(UserRepository userRepository) {
        this.mUserRepository = userRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        ProfileViewModel viewModel = new ProfileViewModel(mUserRepository);
        return (T) viewModel;
    }
}
