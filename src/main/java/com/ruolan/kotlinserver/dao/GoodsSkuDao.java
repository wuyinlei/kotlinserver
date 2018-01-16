package com.ruolan.kotlinserver.dao;

import com.ruolan.kotlinserver.model.GoodsSku;

import java.util.List;

public interface GoodsSkuDao {

    int deleteByPrimaryKey(Integer paramInteger);

    int insert(GoodsSku paramGoodsSku);

    int insertSelective(GoodsSku paramGoodsSku);

    GoodsSku selectByPrimaryKey(Integer paramInteger);

    int updateByPrimaryKeySelective(GoodsSku paramGoodsSku);

    int updateByPrimaryKey(GoodsSku paramGoodsSku);

    List<GoodsSku> selectGoodsSkuList(Integer paramInteger);

}
