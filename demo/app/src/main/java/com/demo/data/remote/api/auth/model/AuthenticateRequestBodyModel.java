package com.demo.data.remote.api.auth.model;

import com.demo.corelib.model.api.Captcha;

public class AuthenticateRequestBodyModel {
    private String username;
    private String password;

    private Captcha captcha;

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

    public Captcha getCaptcha() {
        return captcha;
    }

    public void setCaptcha(Captcha captcha) {
        this.captcha = captcha;
    }
}
