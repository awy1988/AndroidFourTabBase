package com.demo.di.module;

import androidx.lifecycle.ViewModelProviders;
import com.demo.data.UserRepository;
import com.demo.ui.auth.LoginActivity;
import com.demo.ui.auth.viewmodel.LoginViewModel;
import com.demo.ui.auth.viewmodel.factory.LoginViewModelFactory;
import com.demo.ui.main.ProfileFragment;
import com.demo.ui.main.viewmodel.ProfileViewModel;
import com.demo.ui.main.viewmodel.factory.ProfileViewModelFactory;
import dagger.Module;
import dagger.Provides;

@Module(includes = {AccountModule.class})
public class ViewModelProviderModule {

    private LoginActivity mLoginActivity;

    private ProfileFragment mProfileFragment;

    public ViewModelProviderModule(ProfileFragment fragment) {
        this.mProfileFragment = fragment;
    }

    public ViewModelProviderModule(LoginActivity activity) {
        this.mLoginActivity = activity;
    }


    @Provides
    public LoginViewModelFactory provideLoginViewModelFactory(UserRepository userRepository) {
        return new LoginViewModelFactory(userRepository);
    }

    @Provides
    public ProfileViewModelFactory provideProfileViewModelFactory(UserRepository userRepository) {
        return new ProfileViewModelFactory(userRepository);
    }

    @Provides
    public ProfileViewModel provideProfileViewModel(ProfileViewModelFactory factory) {
        return ViewModelProviders.of(this.mProfileFragment, factory).get(ProfileViewModel.class);
    }

    @Provides
    public LoginViewModel provideLoginViewModel(LoginViewModelFactory factory) {
        return ViewModelProviders.of(this.mLoginActivity, factory).get(LoginViewModel.class);
    }

}
