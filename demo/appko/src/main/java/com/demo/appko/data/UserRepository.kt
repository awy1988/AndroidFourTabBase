package com.demo.appko.data

import com.demo.appko.data.remote.api.AuthService
import com.demo.corelib.model.common.LinksModel
import com.demo.corelib.network.base.HandleResponseHeaderRequestCallbackListener
import okhttp3.Headers

class UserRepository(private val authService: AuthService) {

    fun login(
        userName: String,
        password: String,
        captchaText: String,
        encryptedData: String,
        callbackListener: HandleResponseHeaderRequestCallbackListener<Any>
    ) {
        authService.authorizations(userName, password, captchaText, encryptedData, callbackListener)
    }

}
