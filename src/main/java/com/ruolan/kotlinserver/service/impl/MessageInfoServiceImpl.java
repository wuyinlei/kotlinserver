package com.ruolan.kotlinserver.service.impl;

import com.ruolan.kotlinserver.dao.MessageInfoDao;
import com.ruolan.kotlinserver.model.MessageInfo;
import com.ruolan.kotlinserver.service.MessageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MessageInfoServiceImpl implements MessageInfoService {

    @Autowired
    MessageInfoDao messageInfoDao;

    @Override
    public int addMessage(MessageInfo messageInfo) {
        return messageInfoDao.insert(messageInfo);
    }

    @Override
    public List<MessageInfo> getMessageList(Map map) {
        return messageInfoDao.selectMessageList(map);
    }

    @Override
    public List<MessageInfo> getAllMessageListCount(int userId) {
        return messageInfoDao.selectAllMessageList(userId);
    }

}
