package com.docomo.watchy.model.api;

public class AuthorizationRequestBodyModel {
    private String username;
    private String password;
    private CaptchaModel captcha;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CaptchaModel getCaptcha() {
        return captcha;
    }

    public void setCaptcha(CaptchaModel captcha) {
        this.captcha = captcha;
    }
}
