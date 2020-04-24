package com.demo.network;

import com.demo.model.api.AuthorizationRequestBody;
import com.demo.model.api.UpdatePasswordRequestBody;
import com.demo.model.api.UpdateProfileRequestBody;
import com.demo.model.auth.UserInfoModel;
import com.demo.network.base.HttpApiHelper;
import com.demo.network.base.RequestCallbackListener;
import com.demo.network.interfaces.IAccountService;

import javax.inject.Inject;

public class AccountService {

    @Inject
    public AccountService() {

    }

    /**
     * 用户登录
     */
    public void authorization(AuthorizationRequestBody authorizationRequestBody, final RequestCallbackListener<UserInfoModel> listener) {
        HttpApiHelper.executeRequest(getAccountService().authorizations(authorizationRequestBody), listener);
    }

    /**
     * 注销登录
     */
    public void logout(final RequestCallbackListener<String> listener) {
        HttpApiHelper.executeRequest(getAccountService().logout(), listener);
    }

    /**
     * 更新密码
     */
    public void updatePassword(UpdatePasswordRequestBody updatePasswordRequestBody, final RequestCallbackListener<String> listener) {
        HttpApiHelper.executeRequest(getAccountService().updatePassword(updatePasswordRequestBody), listener);
    }

    /**
     * 更新登录用户信息
     */
    public void updateProfile(UpdateProfileRequestBody updateProfileRequestBody, final RequestCallbackListener<String> listener) {
        HttpApiHelper.executeRequest(getAccountService().updateProfile(updateProfileRequestBody), listener);
    }

    private IAccountService getAccountService() {
        return HttpApiHelper.getRetrofitInstance().create(IAccountService.class);
    }

}
