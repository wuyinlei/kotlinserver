package com.ruolan.kotlinserver.dao;

import com.ruolan.kotlinserver.model.OrderInfo;

import java.util.List;
import java.util.Map;

public interface OrderInfoDao {

    int deleteByPrimaryKey(Integer paramInteger);

    int insert(OrderInfo paramOrderInfo);

    int insertSelective(OrderInfo paramOrderInfo);

    OrderInfo selectByPrimaryKey(Integer paramInteger);

    OrderInfo selectByToken(String token);

    int updateByPrimaryKeySelective(OrderInfo paramOrderInfo);

    int updateByPrimaryKey(OrderInfo paramOrderInfo);

    List<OrderInfo> selectOrderList(Map paramMap);

    List<OrderInfo> selectAllOrderList(Integer paramInteger);


}
