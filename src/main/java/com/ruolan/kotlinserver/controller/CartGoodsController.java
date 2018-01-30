package com.ruolan.kotlinserver.controller;


import com.ruolan.kotlinserver.common.Constants;
import com.ruolan.kotlinserver.domain.AddCartGoodsRequest;
import com.ruolan.kotlinserver.domain.DeleteCartGoodsRequest;
import com.ruolan.kotlinserver.domain.base.BaseResponse;
import com.ruolan.kotlinserver.model.CartGoods;
import com.ruolan.kotlinserver.model.UserInfo;
import com.ruolan.kotlinserver.service.CartGoodsService;
import com.ruolan.kotlinserver.service.UserService;
import com.ruolan.kotlinserver.utils.UserDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping(produces = {"application/json;charset=UTF-8"}, value = {"/cartgoods"})
public class CartGoodsController {

    @Autowired
    CartGoodsService cartGoodsService;

    @Autowired
    UserService userService;


    @RequestMapping(value = {"/getList"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse<List<CartGoods>> getList(HttpServletRequest request) {
        BaseResponse resp = new BaseResponse();

        UserInfo userInfo = UserDefault.getUserInfo(request, userService);

        if (userInfo == null) {
            resp.setMessage(Constants.MESSAGE.NO_PERMISSION);
            resp.setStatus(Constants.CODE.NO_PERMISSION_ERROR_CODE);
            return resp;
        }

        List list = this.cartGoodsService.getCartGoodsList(userInfo.getId());
        if ((list == null) || (list.size() == 0)) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.GET_CART_LIST_EMPTY);
            return resp;
        }

        resp.setStatus(Constants.CODE.SUCCESS_CODE);
        resp.setMessage(Constants.MESSAGE.GET_CART_LIST_SUCCESS);
        resp.setData(list);
        return resp;
    }


    @RequestMapping(value = {"/add"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse<Integer> addCartGoods(@RequestBody AddCartGoodsRequest req, HttpServletRequest request) {
        BaseResponse resp = new BaseResponse();
        UserInfo userInfo = UserDefault.getUserInfo(request, userService);
        if (userInfo == null) {
            resp.setMessage(Constants.MESSAGE.NO_PERMISSION);
            resp.setStatus(Constants.CODE.NO_PERMISSION_ERROR_CODE);
            return resp;
        }
        CartGoods cartGoods = new CartGoods();
        cartGoods.setGoodsId(req.getGoodsId());
        cartGoods.setUserId(userInfo.getId());
        cartGoods.setGoodsIcon(req.getGoodsIcon());
        cartGoods.setGoodsDesc(req.getGoodsDesc());
        cartGoods.setGoodsPrice(req.getGoodsPrice());
        cartGoods.setGoodsCount(req.getGoodsCount());
        cartGoods.setGoodsSku(req.getGoodsSku());
        int updateElement = this.cartGoodsService.addCartGoods(cartGoods);
        if (updateElement == 0) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.ADD_CART_ERROR);
            return resp;
        }

        resp.setMessage(Constants.MESSAGE.ADD_CART_SUSSECC);
        resp.setData(Integer.valueOf(cartGoodsService.getCartGoodsList(userInfo.getId()).size()));
        return resp;
    }


    @RequestMapping(value = {"/delete"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse<Integer> deleteCartGoods(@RequestBody DeleteCartGoodsRequest req, HttpServletRequest request) {
        BaseResponse resp = new BaseResponse();
        UserInfo userInfo = UserDefault.getUserInfo(request, userService);
        if (userInfo == null) {
            resp.setMessage(Constants.MESSAGE.NO_PERMISSION);
            resp.setStatus(Constants.CODE.NO_PERMISSION_ERROR_CODE);
            return resp;
        }

        int deleteElements = this.cartGoodsService.deleteCartGoods(req.getCartIdList());
        if (deleteElements < 0) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.DELETE_CART_ERROR);
            return resp;
        }
        resp.setStatus(0);
        resp.setMessage(Constants.MESSAGE.DELETE_CART_SUCCESS);

        return resp;
    }


}
