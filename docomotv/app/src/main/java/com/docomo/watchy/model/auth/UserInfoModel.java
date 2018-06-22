package com.docomo.watchy.model.auth;

/**
 * Created by anweiyang on 17/12/29.
 */

public class UserInfoModel {

    /**
     * name : 朱松杰
     * username : zhusj
     * email : 896752687@qq.com
     * mobile :
     * id : xKSWDWPhc4ee
     * accessToken : eyJhbGciOiJIUzI1NiJ9.YzNXS1piZXRsTGJJDQp4S1NXRFdQaGM0ZWUNCuacseadvuadsA.IbrlSE-mgOxKM-03Tl9LuxnGWDEbCgtU1U5h0lbWq38
     */

    private String name;
    private String username;
    private String email;
    private String mobile;
    private String id;
    private String logo;
    private String logoLarge;
    private String gender;
    private String signature;
    private String accessToken;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogoLarge() {
        return logoLarge;
    }

    public void setLogoLarge(String logoLarge) {
        this.logoLarge = logoLarge;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
