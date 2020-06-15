package com.demo.appmvp.data.remote.api.auth;

import android.text.TextUtils;
import com.demo.appmvp.data.model.AuthenticateRequestBodyModel;
import com.demo.appmvp.data.model.CaptchaDataModel;
import com.demo.appmvp.data.model.ValidateCaptchaRequestBodyModel;
import com.demo.corelib.model.api.Captcha;
import com.demo.corelib.model.common.ResponseModel;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;

public class AuthService {

    private IAuthService mAuthService;

    public AuthService(IAuthService authService) {
        this.mAuthService = authService;
    }

    /**
     * 获取商品列表
     *  @param username 用户名
     * @param password 密码
     */
    public Observable<Response<ResponseModel>> authorizations(String username, String password, String captchaText, String encryptedData) {
        AuthenticateRequestBodyModel authenticateRequestBodyModel = new AuthenticateRequestBodyModel();
        authenticateRequestBodyModel.setUsername(username);
        authenticateRequestBodyModel.setPassword(password);
        if (!TextUtils.isEmpty(captchaText) && !TextUtils.isEmpty(encryptedData)) {
            Captcha captcha = new Captcha();
            captcha.setText(captchaText);
            captcha.setEncryptedData(encryptedData);
            authenticateRequestBodyModel.setCaptcha(captcha);
        }
        return mAuthService.authorizations(authenticateRequestBodyModel);
    }

    public Observable<ResponseModel<CaptchaDataModel>> captcha(String credential) {
        return mAuthService.captcha(credential);
    }

    public Observable<ResponseModel<Object>>  validateCaptcha(String text, String encryptedData) {
        ValidateCaptchaRequestBodyModel validateCaptchaRequestBodyModel = new ValidateCaptchaRequestBodyModel();
        validateCaptchaRequestBodyModel.setText(text);
        validateCaptchaRequestBodyModel.setEncryptedData(encryptedData);
        return mAuthService.validateCaptcha(validateCaptchaRequestBodyModel);

    }

}
