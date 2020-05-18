package com.demo.di.component;

import com.demo.di.module.ViewModelProviderModule;
import com.demo.ui.main.MainFragment;
import com.demo.ui.main.ProfileFragment;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = { ViewModelProviderModule.class })
public interface FragmentComponent {
    void inject(MainFragment fragment);
    void inject(ProfileFragment fragment);
}
