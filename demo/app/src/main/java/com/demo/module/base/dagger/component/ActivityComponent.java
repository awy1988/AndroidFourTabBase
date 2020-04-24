package com.demo.module.base.dagger.component;

import com.demo.module.MainActivity;
import com.demo.module.startup.StartupActivity;

import dagger.Component;

@Component
public interface ActivityComponent {
   void inject(MainActivity activity);
   void inject(StartupActivity activity);
}
