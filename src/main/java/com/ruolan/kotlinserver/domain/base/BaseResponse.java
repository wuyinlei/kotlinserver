package com.ruolan.kotlinserver.domain.base;

import com.ruolan.kotlinserver.common.Constants;

import java.io.Serializable;


public class BaseResponse<T> implements Serializable {

    protected int status;
    protected String message;
    protected T data;


    public BaseResponse() {
    }

    public BaseResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public BaseResponse(T data) {
        this.status = Constants.CODE.SUCCESS_CODE;
        this.message = Constants.MESSAGE.REQUEST_SUCCESS;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
