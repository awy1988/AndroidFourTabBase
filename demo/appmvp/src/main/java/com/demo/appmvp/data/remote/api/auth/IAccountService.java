package com.demo.appmvp.data.remote.api.auth;

import com.demo.appmvp.data.db.User;
import com.demo.corelib.model.common.ResponseModel;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface IAccountService {
    @GET("/user")
    Observable<ResponseModel<User>> getUserInfo();
}
