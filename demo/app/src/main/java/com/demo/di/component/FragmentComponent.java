package com.demo.di.component;

import com.demo.di.module.AccountModule;
import com.demo.di.module.LoginModule;
import com.demo.ui.main.MainFragment;
import com.demo.ui.main.ProfileFragment;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = { LoginModule.class, AccountModule.class })
public interface FragmentComponent {
    void inject(MainFragment fragment);

    void inject(ProfileFragment fragment);
}
