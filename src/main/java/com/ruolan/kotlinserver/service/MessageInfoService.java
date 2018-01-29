package com.ruolan.kotlinserver.service;

import com.ruolan.kotlinserver.model.MessageInfo;

import java.util.List;

public interface MessageInfoService {

    int addMessage(MessageInfo paramMessageInfo);

    List<MessageInfo> getMessageList(Integer paramInteger);

}
