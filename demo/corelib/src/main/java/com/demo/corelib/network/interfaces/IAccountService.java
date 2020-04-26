package com.demo.corelib.network.interfaces;


import com.demo.corelib.constant.ApiConstant;
import com.demo.corelib.model.api.AuthorizationRequestBody;
import com.demo.corelib.model.api.UpdatePasswordRequestBody;
import com.demo.corelib.model.api.UpdateProfileRequestBody;
import com.demo.corelib.model.auth.UserInfoModel;
import com.demo.corelib.model.common.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface IAccountService {

    @POST(ApiConstant.API_AUTHORIZATIONS)
    Call<ResponseModel<UserInfoModel>> authorizations(@Body AuthorizationRequestBody body);

    @DELETE(ApiConstant.API_AUTHORIZATIONS)
    Call<ResponseModel<String>> logout();

    @PUT(ApiConstant.API_USER_PROFILE_PASSWORD)
    Call<ResponseModel<String>> updatePassword(@Body UpdatePasswordRequestBody body);

    @PATCH(ApiConstant.API_USER_PROFILE)
    Call<ResponseModel<String>> updateProfile(@Body UpdateProfileRequestBody body);

}
