package com.ruolan.kotlinserver.controller;


import com.qiniu.util.Auth;
import com.ruolan.kotlinserver.common.Constants;
import com.ruolan.kotlinserver.domain.base.BaseResponse;
import com.ruolan.kotlinserver.model.UserInfo;
import com.ruolan.kotlinserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@EnableAutoConfiguration
@RequestMapping(produces = {"application/json;charset=UTF-8"}, value = {"/user"})
public class UploadController {

    @Autowired
    UserService userService;

    @RequestMapping(value = {"/getUploadToken"}, method = {RequestMethod.POST})
    @ResponseBody
    public BaseResponse<String> getUploadToken(HttpServletRequest request) {
        UserInfo userInfo = userService.selectByToken(request.getHeader(Constants.MESSAGE.USER_TOKEN));

        //上传凭证必须得登录之后才能进行获取
        if (userInfo == null) {
            return new BaseResponse(Constants.CODE.ERROR_CODE, Constants.MESSAGE.USER_IS_EMPTY);
        }
        String accessKey = "r5Gmrjbc9VZKnVIttU3JInHdW3iwK6SACE2_a-d3";
        String secretKey = "2saF0tYPYvND-oGCpcD8phMHJyhuN4mnffapc7HG";
        String bucket = "ruolan";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        System.out.println(upToken);

        BaseResponse resp = new BaseResponse();
        resp.setStatus(0);
        resp.setMessage("");
        resp.setData(upToken);

        return resp;
    }

}
