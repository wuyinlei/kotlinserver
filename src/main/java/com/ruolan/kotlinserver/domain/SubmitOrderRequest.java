package com.ruolan.kotlinserver.domain;

import com.ruolan.kotlinserver.model.Order;

public class SubmitOrderRequest {

    private Order order;

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
