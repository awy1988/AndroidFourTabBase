package com.demo.ui.auth.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.demo.data.UserRepository;
import com.demo.ui.auth.viewmodel.LoginViewModel;

public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private UserRepository mUserRepository;

    public LoginViewModelFactory(UserRepository userRepository) {
        this.mUserRepository = userRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        LoginViewModel loginViewModel = new LoginViewModel(mUserRepository);

        return (T) loginViewModel;
    }
}
