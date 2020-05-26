package com.demo.appko.data.remote.api

import com.demo.appko.constant.AppModuleApiConstant
import com.demo.appko.data.model.AuthenticateRequestBodyModel
import com.demo.corelib.model.api.Captcha
import com.demo.corelib.network.base.HandleResponseHeaderRequestCallbackListener
import com.demo.corelib.network.base.HttpApiHelper

open class AuthService {

    private var authServiceRequest: IAuthService =
        HttpApiHelper.getRetrofitInstance(AppModuleApiConstant.BASE_URL, AppModuleApiConstant.IS_DEBUG)
            .create(IAuthService::class.java)

    fun authorizations(username:String, password:String, captchaText:String, encryptedData:String, listener: HandleResponseHeaderRequestCallbackListener<Any>) {
        val authenticateRequestBodyModel = AuthenticateRequestBodyModel()
        authenticateRequestBodyModel.username = username
        authenticateRequestBodyModel.password = password
        if (captchaText.isNotEmpty() && encryptedData.isNotEmpty()) {
            val captcha = Captcha()
            captcha.text = captchaText
            captcha.encryptedData = encryptedData
        }
        HttpApiHelper.executeRequest(authServiceRequest.authorizations(authenticateRequestBodyModel), listener)
    }

}
