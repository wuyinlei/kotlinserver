package com.ruolan.kotlinserver.service.impl;

import com.ruolan.kotlinserver.dao.MessageInfoDao;
import com.ruolan.kotlinserver.model.MessageInfo;
import com.ruolan.kotlinserver.service.MessageInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MessageInfoServiceImpl implements MessageInfoService {

    @Autowired
    MessageInfoDao messageInfoDao;

    @Override
    public int addMessage(MessageInfo messageInfo) {
        return messageInfoDao.insert(messageInfo);
    }

    @Override
    public List<MessageInfo> getMessageList(Integer userId) {
        return messageInfoDao.selectMessageList(userId);
    }
}
