package com.demo.data.remote.api.auth;

import android.text.TextUtils;
import com.demo.corelib.model.api.Captcha;
import com.demo.corelib.network.base.HandleResponseHeaderRequestCallbackListener;
import com.demo.corelib.network.base.HttpApiHelper;
import com.demo.corelib.network.base.RequestCallbackListener;
import com.demo.data.remote.api.auth.model.AuthenticateRequestBodyModel;
import com.demo.data.remote.api.auth.model.CaptchaDataModel;
import com.demo.data.remote.api.auth.model.ValidateCaptchaRequestBodyModel;

public class AuthService {

    private IAuthService mAuthService;

    public AuthService(IAuthService authService) {
        this.mAuthService = authService;
    }

    /**
     * 获取商品列表
     *
     * @param username 用户名
     * @param password 密码
     */
    public void authorizations(String username, String password, String captchaText, String encryptedData,
        final HandleResponseHeaderRequestCallbackListener listener) {
        AuthenticateRequestBodyModel authenticateRequestBodyModel = new AuthenticateRequestBodyModel();
        authenticateRequestBodyModel.setUsername(username);
        authenticateRequestBodyModel.setPassword(password);
        if (!TextUtils.isEmpty(captchaText) && !TextUtils.isEmpty(encryptedData)) {
            Captcha captcha = new Captcha();
            captcha.setText(captchaText);
            captcha.setEncryptedData(encryptedData);
            authenticateRequestBodyModel.setCaptcha(captcha);
        }
        HttpApiHelper.executeRequest(mAuthService.authorizations(authenticateRequestBodyModel), listener);
    }

    public void captcha(String credential, RequestCallbackListener<CaptchaDataModel> listener) {
        HttpApiHelper.executeRequest(mAuthService.captcha(credential), listener);
    }

    public void validateCaptcha(String text, String encryptedData, RequestCallbackListener listener) {
        ValidateCaptchaRequestBodyModel validateCaptchaRequestBodyModel = new ValidateCaptchaRequestBodyModel();
        validateCaptchaRequestBodyModel.setText(text);
        validateCaptchaRequestBodyModel.setEncryptedData(encryptedData);
        HttpApiHelper.executeRequest(mAuthService.validateCaptcha(validateCaptchaRequestBodyModel), listener);
    }

}
