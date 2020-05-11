package com.demo.data.remote.api.auth;

import com.demo.corelib.model.common.ResponseModel;
import com.demo.corelib.model.item.Item;
import com.demo.ui.constant.AppModuleApiConstant;
import com.demo.data.remote.api.auth.model.AuthenticateRequestBodyModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IAuthService {

    @POST(AppModuleApiConstant.API_AUTHORIZATIONS)
    Call<ResponseModel<List<Item>>> authorizations(@Body AuthenticateRequestBodyModel authenticateRequestBodyModel);

}
