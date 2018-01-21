package com.ruolan.kotlinserver.controller;


import com.ruolan.kotlinserver.common.Constants;
import com.ruolan.kotlinserver.common.HomeInfo;
import com.ruolan.kotlinserver.model.HomeRealInfo;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@EnableAutoConfiguration
@RequestMapping(produces = {"application/json;charset=UTF-8"}, value = {"/home"})
public class HomeController extends BaseController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getHomeData() {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("code", Constants.CODE.SUCCESS_CODE);
        modelMap.put("success", Constants.MESSAGE.REQUEST_SUCCESS);

        HomeRealInfo realInfo = new HomeRealInfo();

        List<String> mBanner = new ArrayList<>();
        mBanner.add(HomeInfo.HOME_BANNER_ONE);
        mBanner.add(HomeInfo.HOME_BANNER_TWO);
        mBanner.add(HomeInfo.HOME_BANNER_THREE);
        mBanner.add(HomeInfo.HOME_BANNER_FOUR);
        realInfo.setBanners(mBanner);

        List<String> mDiscounts = new ArrayList<>();
        mDiscounts.add(HomeInfo.HOME_DISCOUNT_ONE);
        mDiscounts.add(HomeInfo.HOME_DISCOUNT_TWO);
        mDiscounts.add(HomeInfo.HOME_DISCOUNT_THREE);
        mDiscounts.add(HomeInfo.HOME_DISCOUNT_FOUR);
        mDiscounts.add(HomeInfo.HOME_DISCOUNT_FIVE);
        realInfo.setDiscounts(mDiscounts);

        List<String> mTopics = new ArrayList<>();
        mTopics.add(HomeInfo.HOME_TOPIC_ONE);
        mTopics.add(HomeInfo.HOME_TOPIC_TWO);
        mTopics.add(HomeInfo.HOME_TOPIC_THREE);
        mTopics.add(HomeInfo.HOME_TOPIC_FOUR);
        mTopics.add(HomeInfo.HOME_TOPIC_FIVE);
        realInfo.setTopics(mTopics);

        List<String> mMessage = new ArrayList<>();
        String messageOne = "夏日炎炎，第一波福利还有30秒到达战场\", \"新用户立领1000元优惠券";
        String messageTwo = "秋日嘎嘎，第二波福利还有30秒到达战场\", \"新用户立领2000元优惠券";
        String messageThree = "冬日寒寒，第三波福利还有30秒到达战场\", \"新用户立领900元优惠券";
        mMessage.add(messageOne);
        mMessage.add(messageTwo);
        mMessage.add(messageThree);
        realInfo.setMessage(mMessage);
        modelMap.put("home", realInfo);
        return modelMap;

    }

}
