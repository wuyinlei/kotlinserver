package com.ruolan.kotlinserver.service;

import com.ruolan.kotlinserver.model.GoodsSku;

import java.util.List;

public interface GoodsService {

    int addGoodsSku(GoodsSku paramGoodsSku);

    List<GoodsSku> getGoodsSkuList(Integer paramInteger);

}
