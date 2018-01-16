package com.ruolan.kotlinserver.domain;

import com.ruolan.kotlinserver.domain.base.BaseRequest;

public class LoginRequest extends BaseRequest {

    private String mobile;
    private String password;
    private String pushId;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
