package com.demo.appmvp.di.component;

import com.demo.appmvp.di.module.PresenterProviderModule;
import com.demo.appmvp.ui.auth.LoginActivity;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = { PresenterProviderModule.class  })
public interface ActivityComponent {
    void inject(LoginActivity loginActivity);
}
