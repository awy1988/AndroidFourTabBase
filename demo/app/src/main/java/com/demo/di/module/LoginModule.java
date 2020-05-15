package com.demo.di.module;

import com.demo.corelib.network.base.HttpApiHelper;
import com.demo.data.UserRepository;
import com.demo.data.remote.api.auth.AccountService;
import com.demo.data.remote.api.auth.AuthService;
import com.demo.data.remote.api.auth.IAuthService;
import com.demo.ui.constant.AppModuleApiConstant;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class LoginModule {

    @Singleton
    @Provides
    public UserRepository provideUserRepository(AuthService authService, AccountService accountService) {
        return new UserRepository(authService, accountService);
    }

    @Provides
    public IAuthService provideIAuthService() {
        return HttpApiHelper.getRetrofitInstance(AppModuleApiConstant.BASE_URL, AppModuleApiConstant.IS_DEBUG)
            .create(IAuthService.class);
    }
}
