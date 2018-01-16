package com.ruolan.kotlinserver.service.impl;

import com.ruolan.kotlinserver.dao.UserDao;
import com.ruolan.kotlinserver.model.UserInfo;
import com.ruolan.kotlinserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserInfo getUserById(int userId) {
        return userDao.selectByPrimaryKey(Integer.valueOf(userId));
    }

    @Override
    public UserInfo getUserByMobile(String mobile) {
        return userDao.selectByMobile(mobile);
    }

    @Override
    public int addUser(UserInfo paramUserInfo) {
       return userDao.insert(paramUserInfo);
    }

    @Override
    public int modifyUser(UserInfo paramUserInfo) {
        return userDao.updateByPrimaryKey(paramUserInfo);
    }
}
