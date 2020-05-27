package com.demo.appko.di.component

import com.demo.appko.di.module.AuthModule
import com.demo.appko.ui.auth.LoginActivity
import dagger.Component

@Component(modules = [AuthModule::class])
interface AuthComponent {
    fun inject(loginActivity: LoginActivity)

    @Component.Builder
    interface Builder {
        fun build() : AuthComponent
        fun authModule(authModule: AuthModule) : Builder
    }
}
