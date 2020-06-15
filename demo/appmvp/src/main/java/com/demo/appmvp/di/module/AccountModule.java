package com.demo.appmvp.di.module;

import com.demo.appmvp.constant.AppMvpModuleApiConstant;
import com.demo.appmvp.data.UserRepository;
import com.demo.appmvp.data.db.AppDatabase;
import com.demo.appmvp.data.remote.api.auth.AccountService;
import com.demo.appmvp.data.remote.api.auth.AuthService;
import com.demo.appmvp.data.remote.api.auth.IAccountService;
import com.demo.appmvp.data.remote.api.auth.IAuthService;
import com.demo.corelib.network.base.HttpApiHelper;
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
        return HttpApiHelper.getRetrofitInstance(AppMvpModuleApiConstant.BASE_URL, AppMvpModuleApiConstant.IS_DEBUG)
            .create(IAuthService.class);
    }

    @Provides
    public IAccountService provideIAccountService() {
        return HttpApiHelper.getRetrofitInstance(AppMvpModuleApiConstant.BASE_URL, AppMvpModuleApiConstant.IS_DEBUG)
            .create(IAccountService.class);
    }
}
