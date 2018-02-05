package com.ruolan.kotlinserver.service.impl;

import com.ruolan.kotlinserver.dao.GoodsInfoDao;
import com.ruolan.kotlinserver.dao.GoodsSkuDao;
import com.ruolan.kotlinserver.model.GoodsInfo;
import com.ruolan.kotlinserver.model.GoodsSku;
import com.ruolan.kotlinserver.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsSkuDao goodsSkuDao;

    @Autowired
    private GoodsInfoDao goodsInfoDao;

    @Override
    public int addGoods(GoodsInfo paramGoodsInfo) {
        return goodsInfoDao.insert(paramGoodsInfo);
    }

    @Override
    public List<GoodsInfo> getGoodsList(Integer categoryId, Integer pageNo) {
        Map map = new HashMap();
        map.put("categoryId", categoryId);
        map.put("beginIndex", Integer.valueOf((pageNo.intValue() - 1) * 6));
        map.put("pageSize", Integer.valueOf(6));
        return this.goodsInfoDao.selectGoodsList(map);
    }

    @Override
    public List<GoodsInfo> getAllGoodsList(Integer categoryId) {
        return goodsInfoDao.selectAllGoodsList(categoryId);
    }

    @Override
    public GoodsInfo getGoodsDetail(Integer goodsId) {
        return goodsInfoDao.selectByPrimaryKey(goodsId);
    }

    public int addGoodsSku(GoodsSku model) {
        return this.goodsSkuDao.insert(model);
    }

    public List<GoodsSku> getGoodsSkuList(Integer goodsId) {
        return this.goodsSkuDao.selectGoodsSkuList(goodsId);
    }

    @Override
    public int modifyGoodsInfo(GoodsInfo goodsInfo) {
        return this.goodsInfoDao.updateByPrimaryKey(goodsInfo);
    }

    @Override
    public List<GoodsInfo> getGoodsListByKeyword(String keyword, Integer pageNo) {
        Map map = new HashMap();
        map.put("keyword", keyword);
        map.put("beginIndex", Integer.valueOf((pageNo.intValue() - 1) * 6));
        map.put("pageSize", Integer.valueOf(6));
        return this.goodsInfoDao.selectGoodsListByKeyword(map);
    }

    @Override
    public List<GoodsInfo> getAllByKeyword(String keyword) {
        return this.goodsInfoDao.selectAllByKeyword(keyword);
    }

}
