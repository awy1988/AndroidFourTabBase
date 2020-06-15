package com.demo.appmvp.data.model;

public class ValidateCaptchaRequestBodyModel {
    private String encryptedData;
    private String text;

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
