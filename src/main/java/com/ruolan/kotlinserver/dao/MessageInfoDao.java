package com.ruolan.kotlinserver.dao;

import com.ruolan.kotlinserver.model.MessageInfo;

import java.util.List;
import java.util.Map;

public interface MessageInfoDao {

    int deleteByPrimaryKey(Integer paramInteger);

    int insert(MessageInfo paramMessageInfo);

    int insertSelective(MessageInfo paramMessageInfo);

    MessageInfo selectByPrimaryKey(Integer paramInteger);

    int updateByPrimaryKeySelective(MessageInfo paramMessageInfo);

    int updateByPrimaryKey(MessageInfo paramMessageInfo);

    List<MessageInfo> selectMessageList(Map map);

    List<MessageInfo> selectAllMessageList(int userId);

}
