package com.ruolan.kotlinserver.utils;

import com.ruolan.kotlinserver.common.Constants;
import com.ruolan.kotlinserver.model.UserInfo;
import com.ruolan.kotlinserver.service.UserService;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class UserDefault {

    public static UserInfo getUserInfo(HttpServletRequest request, UserService userService) {
        String token = request.getHeader(Constants.MESSAGE.USER_TOKEN);
        if (!StringUtils.isEmpty(token)) {
            UserInfo userInfo = userService.selectByToken(token);
            if (userInfo != null) {
                return userInfo;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
