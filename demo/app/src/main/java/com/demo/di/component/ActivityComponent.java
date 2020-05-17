package com.demo.di.component;

import com.demo.di.module.AccountModule;
import com.demo.ui.auth.LoginActivity;
import com.demo.ui.main.ProfileFragment;
import dagger.Component;

//@Singleton
@Component(modules = { AccountModule.class  })
public interface ActivityComponent {
    void inject(LoginActivity loginActivity);
    void inject(ProfileFragment profileFragment);
}
