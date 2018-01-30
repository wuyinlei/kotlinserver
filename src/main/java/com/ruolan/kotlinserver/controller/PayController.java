package com.ruolan.kotlinserver.controller;


import com.ruolan.kotlinserver.common.Constants;
import com.ruolan.kotlinserver.domain.GetSignRequest;
import com.ruolan.kotlinserver.domain.base.BaseResponse;
import com.ruolan.kotlinserver.utils.YuanFenConverter;
import com.ruolan.kotlinserver.utils.pay.PaySignUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
@RequestMapping(produces = {"application/json;charset=UTF-8"}, value = {"/pay"})
public class PayController {


    @Transactional
    @RequestMapping(value = {"/getPaySign"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse<String> getPaySign(@RequestBody GetSignRequest req) {
        BaseResponse resp = new BaseResponse();

        try {
            resp.setStatus(Constants.CODE.SUCCESS_CODE);
            resp.setMessage(Constants.MESSAGE.GET_PAY_SIGN_SUCCESS);
            resp.setData(PaySignUtils.sign(YuanFenConverter.changeF2Y(String.valueOf(req.getTotalPrice())), req.getOrderId() + ""));
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.GET_PAY_SIGN_ERROR);
            return resp;
        }


    }

}
