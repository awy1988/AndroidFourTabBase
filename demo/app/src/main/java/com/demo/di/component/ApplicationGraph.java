package com.demo.di.component;

import com.demo.di.module.LoginModule;
import com.demo.ui.auth.viewmodel.LoginViewModel;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = LoginModule.class)
public interface ApplicationGraph {
    void inject(LoginViewModel viewModel);
}
