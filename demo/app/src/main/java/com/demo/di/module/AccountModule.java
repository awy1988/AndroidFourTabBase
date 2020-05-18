package com.demo.di.module;

import com.demo.corelib.network.base.HttpApiHelper;
import com.demo.data.UserRepository;
import com.demo.data.database.AppDatabase;
import com.demo.data.remote.api.auth.AccountService;
import com.demo.data.remote.api.auth.AuthService;
import com.demo.data.remote.api.auth.IAccountService;
import com.demo.data.remote.api.auth.IAuthService;
import com.demo.ui.constant.AppModuleApiConstant;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(includes = { DatabaseModule.class })
public class AccountModule {

    @Provides
    public AuthService provideAuthService(IAuthService authService) {
        return new AuthService(authService);
    }

    @Provides
    public AccountService provideAccountService(IAccountService accountService) {
        return new AccountService(accountService);
    }

    @Singleton
    @Provides
    public UserRepository provideUserRepository(AuthService authService, AccountService accountService, AppDatabase appDatabase) {
        return new UserRepository(authService, accountService, appDatabase);
    }

    @Provides
    public IAuthService provideIAuthService() {
        return HttpApiHelper.getRetrofitInstance(AppModuleApiConstant.BASE_URL, AppModuleApiConstant.IS_DEBUG)
            .create(IAuthService.class);
    }

    @Provides
    public IAccountService provideIAccountService() {
        return HttpApiHelper.getRetrofitInstance(AppModuleApiConstant.BASE_URL, AppModuleApiConstant.IS_DEBUG)
            .create(IAccountService.class);
    }
}
