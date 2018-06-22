package com.docomo.watchy.network.interfaces;

import com.docomo.watchy.constant.ApiConstant;
import com.docomo.watchy.model.api.AuthorizationRequestBodyModel;
import com.docomo.watchy.model.api.UpdatePasswordRequestBodyModel;
import com.docomo.watchy.model.api.UpdateProfileRequestBodyModel;
import com.docomo.watchy.model.auth.UserInfoModel;
import com.docomo.watchy.model.common.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface IAccountService {

    @POST(ApiConstant.API_AUTHORIZATIONS)
    Call<ResponseModel<UserInfoModel>> authorizations(@Body AuthorizationRequestBodyModel body);

    @DELETE(ApiConstant.API_AUTHORIZATIONS)
    Call<ResponseModel<String>> logout();

    @PUT(ApiConstant.API_USER_PROFILE_PASSWORD)
    Call<ResponseModel<String>> updatePassword(@Body UpdatePasswordRequestBodyModel body);

    @PATCH(ApiConstant.API_USER_PROFILE)
    Call<ResponseModel<String>> updateProfile(@Body UpdateProfileRequestBodyModel body);

}
