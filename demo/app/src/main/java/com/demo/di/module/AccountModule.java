package com.demo.di.module;

import com.demo.corelib.network.base.HttpApiHelper;
import com.demo.data.remote.api.auth.IAccountService;
import com.demo.ui.constant.AppModuleApiConstant;
import dagger.Module;
import dagger.Provides;

@Module
public class AccountModule {

    @Provides
    public IAccountService provideAccountService() {
        return HttpApiHelper.getRetrofitInstance(AppModuleApiConstant.BASE_URL, AppModuleApiConstant.IS_DEBUG)
            .create(IAccountService.class);
    }
}
