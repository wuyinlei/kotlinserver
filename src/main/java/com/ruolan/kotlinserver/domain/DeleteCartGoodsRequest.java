package com.ruolan.kotlinserver.domain;

import java.util.List;

public class DeleteCartGoodsRequest {

    private List<Integer> cartIdList;

    public List<Integer> getCartIdList() {
        return this.cartIdList;
    }

    public void setCartIdList(List<Integer> cartIdList) {
        this.cartIdList = cartIdList;
    }

}
