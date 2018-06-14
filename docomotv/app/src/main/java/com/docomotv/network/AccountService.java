package com.docomotv.network;

import com.docomotv.model.api.AuthorizationRequestBody;
import com.docomotv.model.api.UpdatePasswordRequestBody;
import com.docomotv.model.api.UpdateProfileRequestBody;
import com.docomotv.model.auth.UserInfoModel;
import com.docomotv.network.base.HttpApiHelper;
import com.docomotv.network.base.RequestCallbackListener;
import com.docomotv.network.interfaces.IAccountService;

public class AccountService {

    /**
     * 用户登录
     */
    public static void authorization(AuthorizationRequestBody authorizationRequestBody, final RequestCallbackListener<UserInfoModel> listener) {
        HttpApiHelper.executeRequest(HttpApiHelper.getRetrofitInstance().create(IAccountService.class).authorizations(authorizationRequestBody), listener);
    }

    /**
     * 注销登录
     */
    public static void logout(final RequestCallbackListener<String> listener) {
        HttpApiHelper.executeRequest(HttpApiHelper.getRetrofitInstance().create(IAccountService.class).logout(), listener);
    }

    /**
     * 更新密码
     */
    public static void updatePassword(UpdatePasswordRequestBody updatePasswordRequestBody, final RequestCallbackListener<String> listener) {
        HttpApiHelper.executeRequest(HttpApiHelper.getRetrofitInstance().create(IAccountService.class).updatePassword(updatePasswordRequestBody), listener);
    }

    /**
     * 更新登录用户信息
     */
    public static void updateProfile(UpdateProfileRequestBody updateProfileRequestBody, final RequestCallbackListener<String> listener) {
        HttpApiHelper.executeRequest(HttpApiHelper.getRetrofitInstance().create(IAccountService.class).updateProfile(updateProfileRequestBody), listener);
    }

}
