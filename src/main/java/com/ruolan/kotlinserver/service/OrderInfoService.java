package com.ruolan.kotlinserver.service;

import com.ruolan.kotlinserver.model.OrderGoods;
import com.ruolan.kotlinserver.model.OrderInfo;
import com.ruolan.kotlinserver.model.UserInfo;
import com.ruolan.kotlinserver.model.UserInfo;

import java.util.List;

public interface OrderInfoService {

    int addOrder(OrderInfo paramOrderInfo);

    List<OrderInfo> getOrderList(Integer userId, Integer status);

    int addOrderGoods(OrderGoods paramOrderGoods);

    OrderInfo getOrderById(Integer paramInteger);

    List<OrderGoods> getOrderGoodsList(Integer paramInteger);

    int modifyOrder(OrderInfo paramOrderInfo);

    OrderInfo getOrderByToken(String token);

}
