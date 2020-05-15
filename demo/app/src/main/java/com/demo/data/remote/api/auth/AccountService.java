package com.demo.data.remote.api.auth;

import com.demo.corelib.network.base.HttpApiHelper;
import com.demo.corelib.network.base.RequestCallbackListener;
import com.demo.data.database.User;
import javax.inject.Inject;

public class AccountService {

    private IAccountService mAccountService;

    @Inject
    public AccountService(IAccountService accountService) {
        this.mAccountService = accountService;
    }

    public void getUserInfo(RequestCallbackListener<User> listener) {
        HttpApiHelper.executeRequest(this.mAccountService.getUserInfo(), listener);
    }


}
