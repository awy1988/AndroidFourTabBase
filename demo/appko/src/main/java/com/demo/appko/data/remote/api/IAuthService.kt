package com.demo.appko.data.remote.api

import com.demo.appko.constant.AppModuleApiConstant
import com.demo.appko.data.model.AuthenticateRequestBodyModel
import com.demo.corelib.model.common.ResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthService {

    @POST(AppModuleApiConstant.API_AUTHORIZATIONS)
    fun authorizations(@Body authenticateRequestBodyModel: AuthenticateRequestBodyModel): Call<ResponseModel<Any>>

}
