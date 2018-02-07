package com.ruolan.kotlinserver.controller;


import com.ruolan.kotlinserver.common.Constants;
import com.ruolan.kotlinserver.domain.AddShipAddressRequest;
import com.ruolan.kotlinserver.domain.DeleteShipAddressRequest;
import com.ruolan.kotlinserver.domain.ModifyShipAddressRequest;
import com.ruolan.kotlinserver.domain.base.BaseResponse;
import com.ruolan.kotlinserver.model.ShipAddress;
import com.ruolan.kotlinserver.model.UserInfo;
import com.ruolan.kotlinserver.service.ShipAddressService;
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
@RequestMapping(produces = {"application/json;charset=UTF-8"}, value = {"/address"})
public class AddressController {

    @Autowired
    UserService userService;
    public static final int IS_DEFAULT = 0;
    public static final int IS_NOT_DEFAULT = 1;

    @Autowired
    private ShipAddressService shipAddressService;

    @RequestMapping(value = {"/getlist"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse<List<ShipAddress>> getList(HttpServletRequest request) {
        BaseResponse resp = new BaseResponse();

        UserInfo userInfo = UserDefault.getUserInfo(request, userService);
        if (userInfo == null) {
            resp.setMessage(Constants.MESSAGE.NO_PERMISSION);
            resp.setStatus(Constants.CODE.NO_PERMISSION_ERROR_CODE);
            return resp;
        }


        List list = this.shipAddressService.getShipAddress(userInfo.getId());
        if ((list.isEmpty())) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.ADDRESS_LIST_EMPTY);
            return resp;
        }

        resp.setStatus(Constants.CODE.SUCCESS_CODE);
        resp.setMessage(Constants.MESSAGE.ADDRESS_LIST_SUCCESS);
        resp.setData(list);
        return resp;
    }

    @RequestMapping(value = {"/add"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse add(HttpServletRequest request, @RequestBody AddShipAddressRequest req) {
        BaseResponse resp = new BaseResponse();

        UserInfo userInfo = UserDefault.getUserInfo(request, userService);
        if (userInfo == null) {
            resp.setMessage(Constants.MESSAGE.NO_PERMISSION);
            resp.setStatus(Constants.CODE.NO_PERMISSION_ERROR_CODE);
            return resp;
        }


        ShipAddress address = new ShipAddress();
        address.setShipAddress(req.getShipAddress());
        address.setShipUserMobile(req.getShipUserMobile());
        address.setShipUserName(req.getShipUserName());
        address.setUserId(userInfo.getId());
        List list = this.shipAddressService.getShipAddress(userInfo.getId());
        if ((list.isEmpty()))
            address.setShipIsDefault(0);
        else {
            address.setShipIsDefault(1);
        }
        this.shipAddressService.addShipAddress(address);
        resp.setStatus(Constants.CODE.SUCCESS_CODE);
        resp.setMessage(Constants.MESSAGE.ADD_ADDRESS_SUCCESS);
        return resp;
    }

    @RequestMapping(value = {"/modify"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse modify(HttpServletRequest request, @RequestBody ModifyShipAddressRequest req) {
        BaseResponse resp = new BaseResponse();

        UserInfo userInfo = UserDefault.getUserInfo(request, userService);
        if (userInfo == null) {
            resp.setMessage(Constants.MESSAGE.NO_PERMISSION);
            resp.setStatus(Constants.CODE.NO_PERMISSION_ERROR_CODE);
            return resp;
        }


        ShipAddress address = new ShipAddress();
        address.setId(req.getId());
        address.setUserId(userInfo.getId());
        address.setShipAddress(req.getShipAddress());
        address.setShipUserMobile(req.getShipUserMobile());
        address.setShipUserName(req.getShipUserName());
        address.setShipIsDefault(req.getShipIsDefault());

        if (req.getShipIsDefault() == 0) {
            List<ShipAddress> shipAddressList = this.shipAddressService.getShipAddress(userInfo.getId());
            for (ShipAddress shipAddress :shipAddressList ) {

                shipAddress.setShipIsDefault(1);
                this.shipAddressService.modifyShipAddress(shipAddress);
            }

        }

        int updateElement = this.shipAddressService.modifyShipAddress(address);
        if (updateElement == 0){
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.MODIFY_ADDRESS_ERROR);
            return resp;
        }
        resp.setStatus(Constants.CODE.SUCCESS_CODE);
        resp.setMessage(Constants.MESSAGE.MODIFY_ADDRESS_SUCCESS);
        return resp;
    }

    @RequestMapping(value = {"/delete"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse delete(HttpServletRequest request, @RequestBody DeleteShipAddressRequest req) {
        BaseResponse resp = new BaseResponse();

        UserInfo userInfo = UserDefault.getUserInfo(request, userService);
        if (userInfo == null) {
            resp.setMessage(Constants.MESSAGE.NO_PERMISSION);
            resp.setStatus(Constants.CODE.NO_PERMISSION_ERROR_CODE);
            return resp;
        }


        boolean isDefault = this.shipAddressService.getShipAddressById(req.getId()).getShipIsDefault() == 0;
        this.shipAddressService.deleteShipAddress(req.getId());
        List list = this.shipAddressService.getShipAddress(userInfo.getId());
        if ((isDefault) && (list != null) && (list.size() > 0)) {
            ShipAddress address = (ShipAddress) list.get(0);
            address.setShipIsDefault(0);
            this.shipAddressService.modifyShipAddress(address);
        }

        resp.setStatus(Constants.CODE.SUCCESS_CODE);
        resp.setMessage(Constants.MESSAGE.DELETE_ADDRESS_SUCCESS);
        return resp;
    }

}
