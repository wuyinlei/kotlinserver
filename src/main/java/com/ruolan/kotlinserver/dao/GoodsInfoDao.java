package com.ruolan.kotlinserver.dao;

import com.ruolan.kotlinserver.model.GoodsInfo;

import java.util.List;
import java.util.Map;

public interface GoodsInfoDao {

    int deleteByPrimaryKey(Integer paramInteger);

    int insert(GoodsInfo paramGoodsInfo);

    int insertSelective(GoodsInfo paramGoodsInfo);

    GoodsInfo selectByPrimaryKey(Integer paramInteger);

    int updateByPrimaryKeySelective(GoodsInfo paramGoodsInfo);

    int updateByPrimaryKeyWithBLOBs(GoodsInfo paramGoodsInfo);

    int updateByPrimaryKey(GoodsInfo paramGoodsInfo);

    List<GoodsInfo> selectAllGoodsList(Integer paramInteger);

    List<GoodsInfo> selectGoodsList(Map paramMap);

    List<GoodsInfo> selectGoodsListByKeyword(Map paramMap);

    List<GoodsInfo> selectAllByKeyword(String paramString);

}
