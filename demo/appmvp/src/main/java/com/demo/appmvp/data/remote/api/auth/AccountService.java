package com.demo.appmvp.data.remote.api.auth;

import com.demo.appmvp.data.db.User;
import com.demo.corelib.model.common.ResponseModel;
import io.reactivex.rxjava3.core.Observable;

public class AccountService {

    private IAccountService mAccountService;

    public AccountService(IAccountService accountService) {
        this.mAccountService = accountService;
    }

    public Observable<ResponseModel<User>> getUserInfo() {
        return this.mAccountService.getUserInfo();
    }


}
