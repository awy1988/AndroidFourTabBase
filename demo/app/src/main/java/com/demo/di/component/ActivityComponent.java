package com.demo.di.component;

import com.demo.ui.auth.LoginActivity;
import dagger.Component;

@Component
public interface ActivityComponent {
    void inject(LoginActivity loginActivity);
}
