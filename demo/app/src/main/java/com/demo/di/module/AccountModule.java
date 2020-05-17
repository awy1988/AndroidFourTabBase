package com.demo.di.module;

import androidx.lifecycle.ViewModelProviders;
import com.demo.corelib.network.base.HttpApiHelper;
import com.demo.data.UserRepository;
import com.demo.data.remote.api.auth.AccountService;
import com.demo.data.remote.api.auth.AuthService;
import com.demo.data.remote.api.auth.IAccountService;
import com.demo.data.remote.api.auth.IAuthService;
import com.demo.ui.auth.LoginActivity;
import com.demo.ui.auth.viewmodel.LoginViewModel;
import com.demo.ui.auth.viewmodel.factory.LoginViewModelFactory;
import com.demo.ui.base.BaseFragment;
import com.demo.ui.constant.AppModuleApiConstant;
import com.demo.ui.main.ProfileFragment;
import com.demo.ui.main.viewmodel.ProfileViewModel;
import com.demo.ui.main.viewmodel.factory.ProfileViewModelFactory;
import dagger.Module;
import dagger.Provides;

@Module
public class AccountModule {


    private BaseFragment mFragment;

    private LoginActivity mBaseActivity;

    private ProfileFragment mProfileFragment;

    public AccountModule(BaseFragment fragment) {
        this.mFragment = fragment;
    }

    public AccountModule(ProfileFragment fragment) {
        this.mProfileFragment = fragment;
    }

    public AccountModule(LoginActivity activity) {
        this.mBaseActivity = activity;
    }

    @Provides
    public AuthService provideAuthService(IAuthService authService) {
        return new AuthService(authService);
    }

    @Provides
    public AccountService provideAccountService(IAccountService accountService) {
        return new AccountService(accountService);
    }

    //@Singleton
    @Provides
    public UserRepository provideUserRepository(AuthService authService, AccountService accountService) {
        return new UserRepository(authService, accountService);
    }

    @Provides
    public IAuthService provideIAuthService() {
        return HttpApiHelper.getRetrofitInstance(AppModuleApiConstant.BASE_URL, AppModuleApiConstant.IS_DEBUG)
            .create(IAuthService.class);
    }

    @Provides
    public IAccountService provideIAccountService() {
        return HttpApiHelper.getRetrofitInstance(AppModuleApiConstant.BASE_URL, AppModuleApiConstant.IS_DEBUG)
            .create(IAccountService.class);
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
        return ViewModelProviders.of(this.mBaseActivity, factory).get(LoginViewModel.class);
    }

}
