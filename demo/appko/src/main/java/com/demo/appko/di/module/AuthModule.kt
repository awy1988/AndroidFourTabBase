package com.demo.appko.di.module

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.demo.appko.constant.AppModuleApiConstant
import com.demo.appko.data.UserRepository
import com.demo.appko.data.remote.api.AuthService
import com.demo.appko.data.remote.api.IAuthService
import com.demo.appko.ui.auth.LoginActivity
import com.demo.appko.ui.auth.viewmodel.LoginViewModel
import com.demo.appko.ui.auth.viewmodel.LoginViewModelFactory
import com.demo.corelib.network.base.HttpApiHelper
import dagger.Module
import dagger.Provides

@Module
class AuthModule {

    private lateinit var loginActivity: LoginActivity

    constructor(loginActivity: LoginActivity) {
        this.loginActivity = loginActivity
    }

    @Provides
    fun provideLoginViewModel(loginViewModelFactory: LoginViewModelFactory) : LoginViewModel {
        return ViewModelProvider(this.loginActivity, loginViewModelFactory).get(LoginViewModel::class.java)
    }

    @Provides
    fun provideLoginViewModelFactory(userRepository: UserRepository) : LoginViewModelFactory{
        return LoginViewModelFactory(userRepository)
    }

    @Provides
    fun provideUserRepository(authService: AuthService) : UserRepository {
        return UserRepository(authService)
    }

    @Provides
    fun providesAuthService(iAuthService: IAuthService) : AuthService {
        return AuthService(iAuthService)
    }

    @Provides
    fun providesIAuthService() : IAuthService {
        return HttpApiHelper.getRetrofitInstance(AppModuleApiConstant.BASE_URL, AppModuleApiConstant.IS_DEBUG)
            .create(IAuthService::class.java)
    }

}
