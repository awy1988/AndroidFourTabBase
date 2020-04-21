package com.demo.network.interfaces;

import com.demo.constant.ApiConstant;
import com.demo.model.api.AuthorizationRequestBody;
import com.demo.model.api.UpdatePasswordRequestBody;
import com.demo.model.api.UpdateProfileRequestBody;
import com.demo.model.auth.UserInfoModel;
import com.demo.model.common.ResponseModel;

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
