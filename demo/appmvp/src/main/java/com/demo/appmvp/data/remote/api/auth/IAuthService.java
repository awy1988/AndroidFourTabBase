package com.demo.appmvp.data.remote.api.auth;

import com.demo.appmvp.constant.AppMvpModuleApiConstant;
import com.demo.appmvp.data.model.AuthenticateRequestBodyModel;
import com.demo.appmvp.data.model.CaptchaDataModel;
import com.demo.appmvp.data.model.ValidateCaptchaRequestBodyModel;
import com.demo.corelib.model.common.ResponseModel;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IAuthService {

    @POST(AppMvpModuleApiConstant.API_AUTHORIZATIONS)
    Observable<Response<ResponseModel>> authorizations(@Body AuthenticateRequestBodyModel authenticateRequestBodyModel);

    @GET(AppMvpModuleApiConstant.API_CAPTCHA)
    Observable<ResponseModel<CaptchaDataModel>> captcha(@Query("credential") String credential);

    @POST(AppMvpModuleApiConstant.API_VALIDATE_CAPTCHA)
    Observable<Response<ResponseModel<Object>>> validateCaptcha(@Body ValidateCaptchaRequestBodyModel validateCaptchaRequestBodyModel);

}
