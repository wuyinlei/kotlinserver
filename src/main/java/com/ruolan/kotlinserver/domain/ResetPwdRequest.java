package com.ruolan.kotlinserver.domain;

import com.ruolan.kotlinserver.domain.base.BaseRequest;

public class ResetPwdRequest extends BaseRequest{

    private String mobile;
    private String newPassword;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
