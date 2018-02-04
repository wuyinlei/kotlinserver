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

@Service
public class OrderGoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsInfoDao goodsInfoDao;

    @Autowired
    private GoodsSkuDao goodsSkuDao;


    public int addGoods(GoodsInfo model) {
        return this.goodsInfoDao.insert(model);
    }

    public List<GoodsInfo> getGoodsList(Integer categoryId, Integer pageNo) {
        Map map = new HashMap();
        map.put("categoryId", categoryId);
        map.put("beginIndex", Integer.valueOf((pageNo.intValue() - 1) * 6));
        map.put("pageSize", Integer.valueOf(6));
        return goodsInfoDao.selectGoodsList(map);
    }

    public List<GoodsInfo> getAllGoodsList(Integer categoryId) {
        return this.goodsInfoDao.selectAllGoodsList(categoryId);
    }

    public GoodsInfo getGoodsDetail(Integer goodsId) {
        return this.goodsInfoDao.selectByPrimaryKey(goodsId);
    }

    public int addGoodsSku(GoodsSku model) {
        return this.goodsSkuDao.insert(model);
    }

    public List<GoodsSku> getGoodsSkuList(Integer goodsId) {
        return this.goodsSkuDao.selectGoodsSkuList(goodsId);
    }

    public int modifyGoodsInfo(GoodsInfo goodsInfo) {
        return this.goodsInfoDao.updateByPrimaryKey(goodsInfo);
    }

    public List<GoodsInfo> getGoodsListByKeyword(String keyword, Integer pageNo) {
        Map map = new HashMap();
        map.put("keyword", keyword);
        map.put("beginIndex", Integer.valueOf((pageNo.intValue() - 1) * 6));
        map.put("pageSize", Integer.valueOf(6));
        return goodsInfoDao.selectGoodsListByKeyword(map);
    }

    public List<GoodsInfo> getAllByKeyword(String keyword) {
        return this.goodsInfoDao.selectAllByKeyword(keyword);
    }
}
