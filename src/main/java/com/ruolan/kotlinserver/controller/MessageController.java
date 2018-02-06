package com.ruolan.kotlinserver.controller;


import com.ruolan.kotlinserver.common.Constants;
import com.ruolan.kotlinserver.domain.GetMessageRequest;
import com.ruolan.kotlinserver.domain.base.BaseResponse;
import com.ruolan.kotlinserver.model.MessageInfo;
import com.ruolan.kotlinserver.model.UserInfo;
import com.ruolan.kotlinserver.service.MessageInfoService;
import com.ruolan.kotlinserver.service.UserService;
import com.ruolan.kotlinserver.utils.UserDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@EnableAutoConfiguration
@RequestMapping(produces = {"application/json;charset=UTF-8"}, value = {"/message"})
public class MessageController {


    @Autowired
    private MessageInfoService messageService;
    @Autowired
    private UserService userService;


    @RequestMapping(value = {"/getList"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse<List<MessageInfo>> getList(HttpServletRequest request,@RequestBody GetMessageRequest req) {
        BaseResponse resp = new BaseResponse();
        UserInfo userInfo = UserDefault.getUserInfo(request,userService);

        if (userInfo == null) {
            resp.setMessage(Constants.MESSAGE.NO_PERMISSION);
            resp.setStatus(Constants.CODE.NO_PERMISSION_ERROR_CODE);
            return resp;
        }

        Map map = new HashMap();
        map.put("userId", userInfo.getId());
        if (req.getPageNo() <0){
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.PAGE_NUM_IS_ILLEGAL);
            return resp;
        }
        map.put("beginIndex", Integer.valueOf((req.getPageNo() - 1) * 10));
        map.put("pageSize", Integer.valueOf(10));

        List<MessageInfo> list = this.messageService.getMessageList(map);
        if (list.isEmpty()) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.GET_MESSAGE_LIST_ERROR);
            return resp;
        }

        resp.setStatus(Constants.CODE.SUCCESS_CODE);
        resp.setMessage(Constants.MESSAGE.GET_MESSAGE_LIST_SUCCESS);
        resp.setData(list);
        return resp;
    }

}
