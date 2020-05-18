package com.demo.di.component;

import com.demo.di.module.ViewModelProviderModule;
import com.demo.ui.auth.LoginActivity;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = { ViewModelProviderModule.class  })
public interface ActivityComponent {
    void inject(LoginActivity loginActivity);
}
