package com.ruolan.kotlinserver.service;

import com.ruolan.kotlinserver.model.MessageInfo;

import java.util.List;
import java.util.Map;

public interface MessageInfoService {

    int addMessage(MessageInfo paramMessageInfo);

    List<MessageInfo> getMessageList(Map map);

    List<MessageInfo> getAllMessageListCount(int  userId);


}
