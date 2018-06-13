package com.docomotv.network;

import com.docomotv.model.api.AuthorizationBody;
import com.docomotv.model.api.UpdatePasswordBody;
import com.docomotv.model.api.UpdateProfileBody;
import com.docomotv.model.auth.UserInfoModel;
import com.docomotv.network.base.HttpApiHelper;
import com.docomotv.network.base.RequestCallbackListener;
import com.docomotv.network.interfaces.IAccountService;

public class AccountService {

    /**
     * 用户登录
     */
    public static void authorization(AuthorizationBody authorizationBody, final RequestCallbackListener<UserInfoModel> listener) {
        HttpApiHelper.executeRequest(HttpApiHelper.getRetrofitInstance().create(IAccountService.class).authorizations(authorizationBody), listener);
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
    public static void updatePassword(UpdatePasswordBody updatePasswordBody, final RequestCallbackListener<String> listener) {
        HttpApiHelper.executeRequest(HttpApiHelper.getRetrofitInstance().create(IAccountService.class).updatePassword(updatePasswordBody), listener);
    }

    /**
     * 更新登录用户信息
     */
    public static void updateProfile(UpdateProfileBody updateProfileBody, final RequestCallbackListener<String> listener) {
        HttpApiHelper.executeRequest(HttpApiHelper.getRetrofitInstance().create(IAccountService.class).updateProfile(updateProfileBody), listener);
    }

}
