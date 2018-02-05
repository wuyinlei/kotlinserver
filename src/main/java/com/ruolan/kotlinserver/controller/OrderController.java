package com.ruolan.kotlinserver.controller;


import com.ruolan.kotlinserver.common.Constants;
import com.ruolan.kotlinserver.domain.GetOrderListRequest;
import com.ruolan.kotlinserver.domain.base.BaseResponse;
import com.ruolan.kotlinserver.model.Order;
import com.ruolan.kotlinserver.model.OrderGoods;
import com.ruolan.kotlinserver.model.OrderInfo;
import com.ruolan.kotlinserver.model.UserInfo;
import com.ruolan.kotlinserver.service.*;
import com.ruolan.kotlinserver.utils.UserDefault;
import com.ruolan.kotlinserver.utils.YuanFenConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping(produces = {"application/json;charset=UTF-8"}, value = {"/order"})
public class OrderController {

    @Autowired
    private OrderInfoService orderService;

    @Autowired
    private ShipAddressService shipAddressService;

    @Autowired
    private CartGoodsService cartGoodsService;

    @Autowired
    private MessageInfoService messageService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/getOrderList"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse<List<Order>> getOrderList(@RequestBody GetOrderListRequest req, HttpServletRequest request) {
        BaseResponse resp = new BaseResponse();

        UserInfo userInfo = UserDefault.getUserInfo(request, userService);
        if (userInfo == null) {
            resp.setMessage(Constants.MESSAGE.NO_PERMISSION);
            resp.setStatus(Constants.CODE.NO_PERMISSION_ERROR_CODE);
            return resp;
        }


        List<OrderInfo> list = this.orderService.getOrderList(userInfo.getId(), req.getOrderStatus());
        if (list.isEmpty()) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.ORDER_LIST_IS_EMPTY);
            return resp;
        }

        List orderList = new ArrayList();
        for (OrderInfo info : list) {
            //创建ordere
            Order order = new Order();
            //订单id
            order.setId(info.getId());
            //订单状态
            order.setOrderStatus(info.getOrderStatus());
            //订单支付状态
            order.setPayType(info.getPayType());
            //订单金额
            order.setTotalPrice(YuanFenConverter.changeY2F(Long.valueOf(info.getTotalPrice())));
            //订单地址
            order.setShipAddress(this.shipAddressService.getShipAddressById(info.getShipId()));
            //订单的商品地址
            List<OrderGoods> goodsList = this.orderService.getOrderGoodsList(info.getId());
            if (goodsList.isEmpty()){
                resp.setStatus(Constants.CODE.ERROR_CODE);
                resp.setMessage(Constants.MESSAGE.ORDER_LIST_IS_EMPTY);
                return resp;
            }
            for (OrderGoods orderGoods : goodsList) {
                //设置订单商品的单价
                orderGoods.setGoodsPrice(YuanFenConverter.changeY2F(orderGoods.getGoodsPrice()));
            }

            order.setOrderGoodsList(goodsList);

            orderList.add(order);
        }

        resp.setStatus(Constants.CODE.SUCCESS_CODE);
        resp.setMessage(Constants.MESSAGE.ORDER_LIST_IS_SUCCESS);
        resp.setData(orderList);
        return resp;
    }



}
