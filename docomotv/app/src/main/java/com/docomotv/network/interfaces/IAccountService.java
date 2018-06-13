package com.docomotv.network.interfaces;

import com.docomotv.constant.ApiConstant;
import com.docomotv.model.api.AuthorizationBody;
import com.docomotv.model.api.UpdatePasswordBody;
import com.docomotv.model.api.UpdateProfileBody;
import com.docomotv.model.auth.UserInfoModel;
import com.docomotv.model.common.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface IAccountService {

    @POST(ApiConstant.API_AUTHORIZATIONS)
    Call<ResponseModel<UserInfoModel>> authorizations(@Body AuthorizationBody body);

    @DELETE(ApiConstant.API_AUTHORIZATIONS)
    Call<ResponseModel<String>> logout();

    @PUT(ApiConstant.API_USER_PROFILE_PASSWORD)
    Call<ResponseModel<String>> updatePassword(@Body UpdatePasswordBody body);

    @PATCH(ApiConstant.API_USER_PROFILE)
    Call<ResponseModel<String>> updateProfile(@Body UpdateProfileBody body);

}
