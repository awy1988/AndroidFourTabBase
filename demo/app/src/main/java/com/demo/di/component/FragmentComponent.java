package com.demo.di.component;

import com.demo.ui.main.MainFragment;
import com.demo.ui.main.ProfileFragment;

//@FragmentScope
//@Component(modules = { ViewModelProviderModule.class, AccountModule.class })
public interface FragmentComponent {
    void inject(MainFragment fragment);

    void inject(ProfileFragment fragment);
}
