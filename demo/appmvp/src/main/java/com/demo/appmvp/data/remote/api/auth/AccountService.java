package com.demo.appmvp.data.remote.api.auth;

import com.demo.appmvp.data.db.User;
import com.demo.corelib.network.base.HttpApiHelper;
import com.demo.corelib.network.base.RequestCallbackListener;

public class AccountService {

    private IAccountService mAccountService;

    public AccountService(IAccountService accountService) {
        this.mAccountService = accountService;
    }

    public void getUserInfo(RequestCallbackListener<User> listener) {
        HttpApiHelper.executeRequest(this.mAccountService.getUserInfo(), listener);
    }


}
