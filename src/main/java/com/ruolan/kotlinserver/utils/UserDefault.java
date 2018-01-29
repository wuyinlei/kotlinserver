package com.ruolan.kotlinserver.utils;

import com.ruolan.kotlinserver.common.Constants;
import com.ruolan.kotlinserver.model.UserInfo;
import com.ruolan.kotlinserver.service.UserService;
import com.ruolan.kotlinserver.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class UserDefault {

    public static UserInfo getUserInfo(HttpServletRequest request) {
        String token = request.getHeader(Constants.MESSAGE.USER_TOKEN);
        UserService service = new UserServiceImpl();
        UserInfo userInfo = service.selectByToken(token);
        if (userInfo != null) {
            return userInfo;
        } else {
            return null;
        }
    }

}
