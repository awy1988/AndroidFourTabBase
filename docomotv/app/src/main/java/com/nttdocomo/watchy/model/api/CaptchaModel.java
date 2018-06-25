package com.nttdocomo.watchy.model.api;

public class CaptchaModel {
    private String text;
    private String hash;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
