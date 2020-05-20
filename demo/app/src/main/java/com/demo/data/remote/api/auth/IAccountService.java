package com.demo.data.remote.api.auth;

import com.demo.corelib.model.common.ResponseModel;
import com.demo.data.db.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IAccountService {
    @GET("/user")
    Call<ResponseModel<User>> getUserInfo();
}
