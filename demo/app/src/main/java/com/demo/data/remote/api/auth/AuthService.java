package com.demo.data.remote.api.auth;

import android.text.TextUtils;
import com.demo.corelib.model.api.Captcha;
import com.demo.corelib.network.base.HandleResponseHeaderRequestCallbackListener;
import com.demo.corelib.network.base.HttpApiHelper;
import com.demo.corelib.network.base.RequestCallbackListener;
import com.demo.data.remote.api.auth.model.CaptchaDataModel;
import com.demo.data.remote.api.auth.model.ValidateCaptchaRequestBodyModel;
import com.demo.ui.constant.AppModuleApiConstant;
import com.demo.data.remote.api.auth.model.AuthenticateRequestBodyModel;

public class AuthService {

    /**
     * TODO 优化逻辑，这个类的使用最好使用依赖注入的方式。
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
        HttpApiHelper.executeRequest(getAuthService().authorizations(authenticateRequestBodyModel), listener);
    }

    public void captcha(String credential, RequestCallbackListener<CaptchaDataModel> listener) {
        HttpApiHelper.executeRequest(getAuthService().captcha(credential), listener);
    }

    public void validateCaptcha(String text, String encryptedData, RequestCallbackListener listener) {
        ValidateCaptchaRequestBodyModel validateCaptchaRequestBodyModel = new ValidateCaptchaRequestBodyModel();
        validateCaptchaRequestBodyModel.setText(text);
        validateCaptchaRequestBodyModel.setEncryptedData(encryptedData);
        HttpApiHelper.executeRequest(getAuthService().validateCaptcha(validateCaptchaRequestBodyModel), listener);
    }

    private IAuthService getAuthService() {
        return HttpApiHelper.getRetrofitInstance(AppModuleApiConstant.BASE_URL, AppModuleApiConstant.IS_DEBUG)
            .create(IAuthService.class);
    }
}
