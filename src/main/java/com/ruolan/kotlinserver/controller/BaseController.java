package com.ruolan.kotlinserver.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {

    protected static final String DEFAULT_CHARTSET = "UTF-8";
    protected static final String DEFAULT_JSON_CONTENT_TYPE = "application/json;charset=UTF-8";
//    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;



}
