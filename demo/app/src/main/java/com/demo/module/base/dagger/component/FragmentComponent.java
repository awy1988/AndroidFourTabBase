package com.demo.module.base.dagger.component;

import com.demo.module.main.MainFragment;
import com.demo.module.main.ProfileFragment;

import dagger.Component;

@Component
public interface FragmentComponent {
   void inject(MainFragment fragment);
   void inject(ProfileFragment fragment);
}
