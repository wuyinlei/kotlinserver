package com.ruolan.kotlinserver.controller;


import com.ruolan.kotlinserver.common.Constants;
import com.ruolan.kotlinserver.domain.GetCategoryRequest;
import com.ruolan.kotlinserver.domain.base.BaseResponse;
import com.ruolan.kotlinserver.model.Category;
import com.ruolan.kotlinserver.model.CategoryFirstData;
import com.ruolan.kotlinserver.service.CategoryService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping(produces = {"application/json;charset=UTF-8"}, value = {"/category"})
public class CategoryController extends BaseController{

    @Autowired
    private CategoryService categoryService;


    /**
     * 获取到商品分类数据  用于首次进入请求数据  默认请求回来所有第一级分类和第一级分类的二级分类
     *
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/getfirstlist", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<List<Category>> getFirstCategoryList() {
        BaseResponse resp = new BaseResponse();

        List<Category> parentList = categoryService.getParentList();

        List<Category> categoryList = categoryService.getCategoryList(1);
        if ((categoryList == null) || (categoryList.size() == 0)) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.CATEGORY_LIST_EMPTY);
            return resp;
        }

        CategoryFirstData data = new CategoryFirstData();
        data.setParent(parentList);
        data.setChildren(categoryList);
        resp.setStatus(Constants.CODE.SUCCESS_CODE);
        resp.setMessage(Constants.MESSAGE.CATEGORY_LIST_SUCCESS);
        resp.setData(data);
        return resp;

    }

    /**
     * 根据第一级商品分类的id  获取二级分类
     * @param categoryId  商品id
     * @return
     */
    @RequestMapping(value = "/getlist", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<List<Category>> getCategoryList(@RequestBody GetCategoryRequest req) {
        BaseResponse resp = new BaseResponse();


        List<Category> categoryList = categoryService.getCategoryList(req.getParentId());
        if ((categoryList == null) || (categoryList.size() == 0)) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.CATEGORY_LIST_EMPTY);
            return resp;
        }

        resp.setData(categoryList);
        resp.setMessage(Constants.MESSAGE.CATEGORY_LIST_SUCCESS);
        resp.setStatus(Constants.CODE.SUCCESS_CODE);
        return resp;

    }


}
