package com.demo.di.component;

import com.demo.di.module.AccountModule;
import com.demo.di.module.LoginModule;
import com.demo.ui.auth.viewmodel.LoginViewModel;
import com.demo.ui.main.viewmodel.MainViewModel;
import com.demo.ui.main.viewmodel.ProfileViewModel;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {LoginModule.class, AccountModule.class })
public interface ApplicationGraph {
    void inject(LoginViewModel viewModel);
    void inject(MainViewModel viewModel);
    void inject(ProfileViewModel viewModel);
}
