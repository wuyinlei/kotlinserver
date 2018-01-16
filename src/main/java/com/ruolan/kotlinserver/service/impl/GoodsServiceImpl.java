package com.ruolan.kotlinserver.service.impl;

import com.ruolan.kotlinserver.dao.GoodsSkuDao;
import com.ruolan.kotlinserver.model.GoodsSku;
import com.ruolan.kotlinserver.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsSkuDao goodsSkuMapper;

    public int addGoodsSku(GoodsSku model) {
        return this.goodsSkuMapper.insert(model);
    }

    public List<GoodsSku> getGoodsSkuList(Integer goodsId) {
        return this.goodsSkuMapper.selectGoodsSkuList(goodsId);
    }

}
