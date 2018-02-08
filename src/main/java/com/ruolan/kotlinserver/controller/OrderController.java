package com.ruolan.kotlinserver.controller;


import com.ruolan.kotlinserver.common.Constants;
import com.ruolan.kotlinserver.common.InitAction;
import com.ruolan.kotlinserver.domain.*;
import com.ruolan.kotlinserver.domain.base.BaseResponse;
import com.ruolan.kotlinserver.model.*;
import com.ruolan.kotlinserver.service.*;
import com.ruolan.kotlinserver.utils.DateUtil;
import com.ruolan.kotlinserver.utils.PushSender;
import com.ruolan.kotlinserver.utils.UserDefault;
import com.ruolan.kotlinserver.utils.YuanFenConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
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

    /**
     * 获取到所有订单
     * @param req
     * @param request
     * @return
     */
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
            resp.setStatus(Constants.CODE.SUCCESS_CODE);
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
//            if (goodsList.isEmpty()) {
//                resp.setStatus(Constants.CODE.SUCCESS_CODE);
//                resp.setMessage(Constants.MESSAGE.ORDER_LIST_IS_EMPTY);
//                return resp;
//            }
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

    /**
     * 获取订单
     * @param req
     * @param request
     * @return
     */
    @RequestMapping(value = {"/getOrderById"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse<Order> getOrderById(@RequestBody GetOrderByIdRequest req, HttpServletRequest request) {
        BaseResponse resp = new BaseResponse();

        UserInfo userInfo = UserDefault.getUserInfo(request, userService);
        if (userInfo == null) {
            resp.setMessage(Constants.MESSAGE.NO_PERMISSION);
            resp.setStatus(Constants.CODE.NO_PERMISSION_ERROR_CODE);
            return resp;
        }

        Order order = new Order();
        OrderInfo orderInfo = this.orderService.getOrderById(req.getOrderId());
        if (orderInfo == null) {
            resp.setStatus(Constants.CODE.SUCCESS_CODE);
            resp.setMessage(Constants.MESSAGE.ORDER_GET_ERROR);
            resp.setData(order);
            return resp;
        }
        order.setId(orderInfo.getId());
        order.setOrderStatus(orderInfo.getOrderStatus());
        order.setPayType(orderInfo.getPayType());
        order.setTotalPrice(YuanFenConverter.changeY2F(Long.valueOf(orderInfo.getTotalPrice())));

        order.setShipAddress(this.shipAddressService.getShipAddressById(orderInfo.getShipId()));

        List<OrderGoods> orderGoodsList = this.orderService.getOrderGoodsList(orderInfo.getId());
        if (!orderGoodsList.isEmpty()) {
            for (OrderGoods orderGoods : orderGoodsList) {
                orderGoods.setGoodsPrice(YuanFenConverter.changeY2F(orderGoods.getGoodsPrice()));
            }
            order.setOrderGoodsList(orderGoodsList);
        }
        resp.setStatus(Constants.CODE.SUCCESS_CODE);
        resp.setMessage(Constants.MESSAGE.ORDER_GET_SUCCESS);
        resp.setData(order);
        return resp;
    }

    /**
     * 提交订单
     * @param req
     * @param request
     * @return
     */
    @Transactional
    @RequestMapping(value = {"/submitOrder"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse submitOrder(@RequestBody SubmitOrderRequest req, HttpServletRequest request) {
        BaseResponse resp = new BaseResponse();

        UserInfo userInfo = UserDefault.getUserInfo(request, userService);
        if (userInfo == null) {
            resp.setMessage(Constants.MESSAGE.NO_PERMISSION);
            resp.setStatus(Constants.CODE.NO_PERMISSION_ERROR_CODE);
            return resp;
        }

        OrderInfo orderInfo = orderService.getOrderById(req.getOrder().getId());
        orderInfo.setOrderStatus(1);
        orderInfo.setShipId(req.getOrder().getShipAddress().getId());

        this.orderService.modifyOrder(orderInfo);

        List cartIdList = InitAction.cartIdMap.get(req.getOrder().getId());
        this.cartGoodsService.deleteCartGoods(cartIdList);
        InitAction.cartIdMap.remove(req.getOrder().getId());

        String pushId = this.userService.getUserById(userInfo.getId()).getPushId();

        sendMessage(userInfo, pushId, req.getOrder().getId() + "");

        resp.setStatus(Constants.CODE.SUCCESS_CODE);
        resp.setMessage(Constants.MESSAGE.ORDER_SUBMIT_SUCCESS);
        return resp;
    }

    /**
     * 取消订单
     * @param req
     * @param request
     * @return
     */
    @RequestMapping(value = {"/cancel"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse cancelOrder(@RequestBody CancelOrderRequest req, HttpServletRequest request) {
        BaseResponse resp = new BaseResponse();

        UserInfo userInfo = UserDefault.getUserInfo(request, userService);
        if (userInfo == null) {
            resp.setMessage(Constants.MESSAGE.NO_PERMISSION);
            resp.setStatus(Constants.CODE.NO_PERMISSION_ERROR_CODE);
            return resp;
        }

        OrderInfo orderInfo = this.orderService.getOrderById(req.getOrderId());
        orderInfo.setOrderStatus(Integer.valueOf(4));
        int updateElement = orderService.modifyOrder(orderInfo);
        if (updateElement == 0) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.CANCEL_ORDER_ERROR);
            return resp;
        } else {

            resp.setStatus(Constants.CODE.SUCCESS_CODE);
            resp.setMessage(Constants.MESSAGE.CANCEL_ORDER_SUCCESS);
            return resp;
        }
    }

    /**
     * 确认订单
     * @param req
     * @param request
     * @return
     */
    @RequestMapping(value = {"/confirm"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse confirmOrder(@RequestBody ConfirmOrderRequest req, HttpServletRequest request) {
        BaseResponse resp = new BaseResponse();


        UserInfo userInfo = UserDefault.getUserInfo(request, userService);
        if (userInfo == null) {
            resp.setMessage(Constants.MESSAGE.NO_PERMISSION);
            resp.setStatus(Constants.CODE.NO_PERMISSION_ERROR_CODE);
            return resp;
        }


        OrderInfo orderInfo = this.orderService.getOrderById(req.getOrderId());

        orderInfo.setOrderStatus(3);
        int updateElement = orderService.modifyOrder(orderInfo);
        if (updateElement == 0) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.ORDER_CONFIRM_ERROR);
            return resp;
        } else {
            resp.setStatus(Constants.CODE.SUCCESS_CODE);
            resp.setMessage(Constants.MESSAGE.ORDER_CONFIRM_SUCCESS);
            return resp;
        }
    }

    /**
     * 订单支付
     * @param req
     * @param request
     * @return
     */
    @RequestMapping(value = {"/pay"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse payOrder(@RequestBody PayOrderRequest req, HttpServletRequest request) {
        BaseResponse resp = new BaseResponse();


        UserInfo userInfo = UserDefault.getUserInfo(request, userService);
        if (userInfo == null) {
            resp.setMessage(Constants.MESSAGE.NO_PERMISSION);
            resp.setStatus(Constants.CODE.NO_PERMISSION_ERROR_CODE);
            return resp;
        }

        OrderInfo orderInfo = this.orderService.getOrderById(req.getOrderId());
        orderInfo.setOrderStatus(Integer.valueOf(2));
        int updateElement = orderService.modifyOrder(orderInfo);
        if (updateElement == 0) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.ORDER_PAY_ERROR);
            return resp;
        } else {
            resp.setStatus(Constants.CODE.SUCCESS_CODE);
            resp.setMessage(Constants.MESSAGE.ORDER_PAY_SUCCESS);
            return resp;
        }
    }

    /**
     * 发送订单消息
     *
     * @param userInfo 用户
     * @param pushId   推送id
     * @param orderId  订单id
     */
    private void sendMessage(UserInfo userInfo, String pushId, String orderId) {
        String curTime = DateUtil.format(new Date(), DateUtil.FORMAT_LONG_NEW);
        MessageInfo msg = new MessageInfo();
        msg.setMsgIcon(userInfo.getUserIcon());
        msg.setMsgTitle("下单成功");
        msg.setMsgContent("恭喜您下单成功，有一笔订单等待支付");
        msg.setMsgTime(curTime);
        msg.setUserId(userInfo.getId());
        this.messageService.addMessage(msg);

        PushSender.sendOrderEvent(pushId, orderId);
    }


}
