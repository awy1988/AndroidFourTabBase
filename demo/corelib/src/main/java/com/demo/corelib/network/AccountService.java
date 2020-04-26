package com.demo.corelib.network;


import com.demo.corelib.model.api.AuthorizationRequestBody;
import com.demo.corelib.model.api.UpdatePasswordRequestBody;
import com.demo.corelib.model.api.UpdateProfileRequestBody;
import com.demo.corelib.model.auth.UserInfoModel;
import com.demo.corelib.network.base.HttpApiHelper;
import com.demo.corelib.network.base.RequestCallbackListener;
import com.demo.corelib.network.interfaces.IAccountService;

public class AccountService {


    /**
     * 用户登录
     */
    public static void authorization(AuthorizationRequestBody authorizationRequestBody, final RequestCallbackListener<UserInfoModel> listener) {
        HttpApiHelper.executeRequest(getAccountService().authorizations(authorizationRequestBody), listener);
    }

    /**
     * 注销登录
     */
    public static void logout(final RequestCallbackListener<String> listener) {
        HttpApiHelper.executeRequest(getAccountService().logout(), listener);
    }

    /**
     * 更新密码
     */
    public static void updatePassword(UpdatePasswordRequestBody updatePasswordRequestBody, final RequestCallbackListener<String> listener) {
        HttpApiHelper.executeRequest(getAccountService().updatePassword(updatePasswordRequestBody), listener);
    }

    /**
     * 更新登录用户信息
     */
    public static void updateProfile(UpdateProfileRequestBody updateProfileRequestBody, final RequestCallbackListener<String> listener) {
        HttpApiHelper.executeRequest(getAccountService().updateProfile(updateProfileRequestBody), listener);
    }

    private static IAccountService getAccountService() {
        return HttpApiHelper.getRetrofitInstance().create(IAccountService.class);
    }

}
