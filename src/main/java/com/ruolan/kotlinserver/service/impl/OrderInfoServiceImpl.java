package com.ruolan.kotlinserver.service.impl;


import com.ruolan.kotlinserver.dao.OrderGoodsDao;
import com.ruolan.kotlinserver.dao.OrderInfoDao;
import com.ruolan.kotlinserver.model.OrderGoods;
import com.ruolan.kotlinserver.model.OrderInfo;
import com.ruolan.kotlinserver.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    OrderInfoDao orderInfoDao;

    @Autowired
    private OrderGoodsDao orderGoodsDao;

    @Override
    public int addOrder(OrderInfo orderInfo) {
        return orderInfoDao.insert(orderInfo);
    }

    @Override
    public List<OrderInfo> getOrderList(Integer userId, Integer orderStatus) {
        if (orderStatus.intValue() == 0) {
            return this.orderInfoDao.selectAllOrderList(userId);
        }
        Map map = new HashMap();
        map.put("userId", userId);
        map.put("orderStatus", orderStatus);
        return this.orderInfoDao.selectOrderList(map);
    }

    public int addOrderGoods(OrderGoods model) {
        return orderGoodsDao.insert(model);
    }

    public OrderInfo getOrderById(Integer id) {
        return this.orderInfoDao.selectByPrimaryKey(id);
    }

    public List<OrderGoods> getOrderGoodsList(Integer orderId) {
        return orderGoodsDao.selectGoodsList(orderId);
    }

    public int modifyOrder(OrderInfo orderInfo) {
        return this.orderInfoDao.updateByPrimaryKey(orderInfo);
    }

    @Override
    public OrderInfo getOrderByToken(String token) {
        return orderInfoDao.selectByToken(token);
    }
}
