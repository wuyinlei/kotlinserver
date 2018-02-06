package com.ruolan.kotlinserver.service.impl;

import com.ruolan.kotlinserver.dao.CartGoodsDao;
import com.ruolan.kotlinserver.model.CartGoods;
import com.ruolan.kotlinserver.service.CartGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cartService")
public class CartGoodsServiceImpl implements CartGoodsService {

    @Autowired
    CartGoodsDao cartGoodsDao;

    @Override
    public int addCartGoods(CartGoods cartGoods) {
        return cartGoodsDao.insert(cartGoods);
    }

    @Override
    public List<CartGoods> getCartGoodsList(Integer cartGoodsId) {
        return cartGoodsDao.selectCartGoodsList(cartGoodsId);
    }

    @Override
    public int deleteCartGoods(List<Integer> paramList) {
        return cartGoodsDao.deleteCartGoods(paramList);
    }

    @Override
    public int updateCartGoods(CartGoods cartGoods) {
        return cartGoodsDao.updateByPrimaryKey(cartGoods);
    }

    @Override
    public CartGoods selectByGoodsId(Integer goodsId) {
        return cartGoodsDao.selectByGoodsId(goodsId);
    }
}
