package com.demo.appmvp.data.remote.api.auth;

import com.demo.appmvp.data.db.User;
import com.demo.corelib.model.common.ResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IAccountService {
    @GET("/user")
    Call<ResponseModel<User>> getUserInfo();
}
