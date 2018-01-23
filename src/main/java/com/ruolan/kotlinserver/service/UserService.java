package com.ruolan.kotlinserver.service;

import com.ruolan.kotlinserver.model.UserInfo;

public interface UserService {

    UserInfo getUserById(int paramInt);

    UserInfo getUserByMobile(String paramString);

    int addUser(UserInfo paramUserInfo);

    int modifyUser(UserInfo paramUserInfo);

    UserInfo selectByToken(String token);

}
