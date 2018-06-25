package com.nttdocomo.watchy.network;

import android.text.TextUtils;

import com.nttdocomo.watchy.model.api.AuthorizationRequestBodyModel;
import com.nttdocomo.watchy.model.api.CaptchaModel;
import com.nttdocomo.watchy.model.api.UpdatePasswordRequestBodyModel;
import com.nttdocomo.watchy.model.api.UpdateProfileRequestBodyModel;
import com.nttdocomo.watchy.model.auth.UserInfoModel;
import com.nttdocomo.watchy.network.common.HttpApiHelper;
import com.nttdocomo.watchy.network.common.RequestCallbackListener;
import com.nttdocomo.watchy.network.interfaces.IAccountService;

public class AccountService {

    /**
     * 用户登录
     */
    public static void authorization(String username, String password, String captchaText, String captchaHash , final RequestCallbackListener<UserInfoModel> listener) {
        AuthorizationRequestBodyModel requestBody = new AuthorizationRequestBodyModel();
        requestBody.setUsername(username);
        requestBody.setPassword(password);
        if (!TextUtils.isEmpty(captchaText) && !TextUtils.isEmpty(captchaHash)) {
            CaptchaModel captcha = new CaptchaModel();
            captcha.setText(captchaText);
            captcha.setHash(captchaHash);
            requestBody.setCaptcha(captcha);
        }
        HttpApiHelper.executeRequest(getAccountService().authorizations(requestBody), listener);
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
    public static void updatePassword(UpdatePasswordRequestBodyModel updatePasswordRequestBody, final RequestCallbackListener<String> listener) {
        HttpApiHelper.executeRequest(getAccountService().updatePassword(updatePasswordRequestBody), listener);
    }

    /**
     * 更新登录用户信息
     */
    public static void updateProfile(UpdateProfileRequestBodyModel updateProfileRequestBody, final RequestCallbackListener<String> listener) {
        HttpApiHelper.executeRequest(getAccountService().updateProfile(updateProfileRequestBody), listener);
    }

    private static IAccountService getAccountService() {
        return HttpApiHelper.getRetrofitInstance().create(IAccountService.class);
    }

}
