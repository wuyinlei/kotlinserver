package com.ruolan.kotlinserver.dao;

import com.ruolan.kotlinserver.model.CartGoods;

import java.util.List;

public interface CartGoodsDao {

    int deleteByPrimaryKey(Integer paramInteger);

    int insert(CartGoods paramCartGoods);

    int insertSelective(CartGoods paramCartGoods);

    CartGoods selectByPrimaryKey(Integer paramInteger);

    int updateByPrimaryKeySelective(CartGoods paramCartGoods);

    int updateByPrimaryKey(CartGoods paramCartGoods);

    List<CartGoods> selectCartGoodsList(int paramInt);

    int deleteCartGoods(List<Integer> paramList);

}
