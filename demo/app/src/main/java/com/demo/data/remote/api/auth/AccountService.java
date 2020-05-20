package com.demo.data.remote.api.auth;

import com.demo.corelib.network.base.HttpApiHelper;
import com.demo.corelib.network.base.RequestCallbackListener;
import com.demo.data.db.User;

public class AccountService {

    private IAccountService mAccountService;

    public AccountService(IAccountService accountService) {
        this.mAccountService = accountService;
    }

    public void getUserInfo(RequestCallbackListener<User> listener) {
        HttpApiHelper.executeRequest(this.mAccountService.getUserInfo(), listener);
    }


}
