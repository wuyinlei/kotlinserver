package com.ruolan.kotlinserver.service;

import com.ruolan.kotlinserver.model.GoodsInfo;
import com.ruolan.kotlinserver.model.GoodsSku;

import java.util.List;

public interface OrderGoodsService {
    int addGoods(GoodsInfo paramGoodsInfo);

    List<GoodsInfo> getGoodsList(Integer paramInteger1, Integer paramInteger2);

    List<GoodsInfo> getAllGoodsList(Integer paramInteger);

    GoodsInfo getGoodsDetail(Integer paramInteger);

    int addGoodsSku(GoodsSku paramGoodsSku);

    List<GoodsSku> getGoodsSkuList(Integer paramInteger);

    void modifyGoodsInfo(GoodsInfo paramGoodsInfo);

    List<GoodsInfo> getGoodsListByKeyword(String paramString, Integer paramInteger);

    List<GoodsInfo> getAllByKeyword(String paramString);
}
