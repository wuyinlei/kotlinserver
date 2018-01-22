package com.ruolan.kotlinserver.controller;


import com.qiniu.util.Auth;
import com.ruolan.kotlinserver.domain.base.BaseResponse;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
@RequestMapping(produces = {"application/json;charset=UTF-8"}, value = {"/user"})
public class UploadController {

    @RequestMapping(value = {"/getUploadToken"}, method = {RequestMethod.POST})
    @ResponseBody
    public BaseResponse<String> getUploadToken() {
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
