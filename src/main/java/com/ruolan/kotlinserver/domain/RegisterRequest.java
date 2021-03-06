package com.ruolan.kotlinserver.domain;

import com.ruolan.kotlinserver.domain.base.BaseRequest;

public class RegisterRequest extends BaseRequest {

    private String mobile;
    private String verifyCode;
    private String pwd;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }


    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
