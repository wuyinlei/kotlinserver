package com.ruolan.kotlinserver.common;

/**
 * 订单状态
 */
public class OrderStatus {

    public static final int ORDER_NEW = -1;  //新订单
    public static final int ORDER_ALL = 0;  //全部订单
    public static final int ORDER_COMPLETED = 1; //已完成订单
    public static final int ORDER_WAIT_CONFIRM = 2; //待确认订单
    public static final int ORDER_WAIT_PAY = 3;  //待支付订单
    public static final int ORDER_CANCELED = 4; //已取消订单

}
