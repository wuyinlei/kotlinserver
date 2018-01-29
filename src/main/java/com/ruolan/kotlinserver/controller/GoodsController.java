package com.ruolan.kotlinserver.controller;


import com.ruolan.kotlinserver.common.Constants;
import com.ruolan.kotlinserver.domain.GetGoodsDetailRequest;
import com.ruolan.kotlinserver.domain.GetGoodsListByKeywordRequest;
import com.ruolan.kotlinserver.domain.GetGoodsListRequest;
import com.ruolan.kotlinserver.domain.base.BaseResponse;
import com.ruolan.kotlinserver.model.GoodsInfo;
import com.ruolan.kotlinserver.model.GoodsSku;
import com.ruolan.kotlinserver.service.GoodsService;
import com.ruolan.kotlinserver.utils.YuanFenConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping(produces = {"application/json;charset=UTF-8"}, value = {"/goods"})
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 获取到商品列表  通过商品id 和 页数
     *
     * @param req GetGoodsListRequest
     * @return
     */
    @RequestMapping(value = {"/getgoodslist"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<List<GoodsInfo>> getGoodsList(@RequestBody GetGoodsListRequest req) {
        BaseResponse resp = new BaseResponse();

        List<GoodsInfo> list = this.goodsService.getGoodsList(req.getCategoryId(), req.getPageNo());
        if ((list == null) || (list.size() == 0)) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.CATEGORY_LIST_EMPTY);
            return resp;
        }

        for (GoodsInfo info : list) {
            info.setGoodsDefaultPrice(YuanFenConverter.changeY2F(info.getGoodsDefaultPrice()));
            info.setMaxPage(Integer.valueOf(this.goodsService.getAllGoodsList(req.getCategoryId()).size() / 6 + 1));
        }

        resp.setStatus(Constants.CODE.SUCCESS_CODE);
        resp.setMessage(Constants.MESSAGE.CATEGORY_LIST_SUCCESS);
        resp.setData(list);
        return resp;
    }

    /**
     * 获取到商品详情信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = {"/getgoodsdetail"}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<GoodsInfo> getGoodsDetail(@RequestBody GetGoodsDetailRequest req) {
        BaseResponse resp = new BaseResponse();

        GoodsInfo goodsInfo = this.goodsService.getGoodsDetail(req.getGoodsId());
        if (goodsInfo == null) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.CATEGORY_LIST_EMPTY);
            return resp;
        }

        goodsInfo.setGoodsDefaultPrice(YuanFenConverter.changeY2F(goodsInfo.getGoodsDefaultPrice()));

        List<GoodsSku> skuList = this.goodsService.getGoodsSkuList(goodsInfo.getId());
        for (GoodsSku sku : skuList) {
            sku.setSkuTitle(sku.getGoodsSkuTitle());
            sku.setSkuContent(Arrays.asList(sku.getGoodsSkuContent().split(",")));
        }
        goodsInfo.setGoodsSku(skuList);

        resp.setStatus(Constants.CODE.SUCCESS_CODE);
        resp.setMessage(Constants.MESSAGE.CATEGORY_DETAIL_SUCCESS);
        resp.setData(goodsInfo);
        return resp;
    }

    /**
     * 模糊查询商品
     *
     * @param req
     * @return
     */
    @RequestMapping(value = {"/getgoodsbykeyword"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse<List<GoodsInfo>> getGoodsListByKeyword(@RequestBody GetGoodsListByKeywordRequest req) {
        BaseResponse resp = new BaseResponse();

        List<GoodsInfo> list = this.goodsService.getGoodsListByKeyword(req.getKeyword(), req.getPageNo());
        if ((list == null) || (list.size() == 0)) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.CATEGORY_LIST_EMPTY);
            return resp;
        }

        for (GoodsInfo info : list) {
            info.setGoodsDefaultPrice(YuanFenConverter.changeY2F(info.getGoodsDefaultPrice()));
            info.setMaxPage(Integer.valueOf(this.goodsService.getAllByKeyword(req.getKeyword()).size() / 6 + 1));
        }

        resp.setStatus(Constants.CODE.SUCCESS_CODE);
        resp.setMessage(Constants.MESSAGE.CATEGORY_LIST_SUCCESS);
        resp.setData(list);
        return resp;
    }

}
