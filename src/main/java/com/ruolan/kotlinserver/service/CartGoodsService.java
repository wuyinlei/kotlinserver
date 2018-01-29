package com.ruolan.kotlinserver.service;

import com.ruolan.kotlinserver.model.CartGoods;

import java.util.List;

public interface CartGoodsService {

    int addCartGoods(CartGoods paramCartGoods);

    List<CartGoods> getCartGoodsList(Integer paramInteger);

    int deleteCartGoods(List<Integer> paramList);

}
