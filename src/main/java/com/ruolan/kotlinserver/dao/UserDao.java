package com.ruolan.kotlinserver.dao;

import com.ruolan.kotlinserver.model.UserInfo;

public interface UserDao {

    int deleteByPrimaryKey(Integer paramInteger);

    int insert(UserInfo paramUserInfo);

    int insertSelective(UserInfo paramUserInfo);

    UserInfo selectByPrimaryKey(Integer paramInteger);

    UserInfo selectByMobile(String paramString);

    int updateByPrimaryKeySelective(UserInfo paramUserInfo);

    int updateByPrimaryKey(UserInfo paramUserInfo);

}
