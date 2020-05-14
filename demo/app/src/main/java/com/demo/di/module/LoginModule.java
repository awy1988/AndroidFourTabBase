package com.demo.di.module;

import com.demo.data.UserRepository;
import com.demo.data.remote.api.auth.AuthService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class LoginModule {

    @Singleton
    @Provides
    public UserRepository provideUserRepository(AuthService authService) {
        return new UserRepository(authService);
    }

}
