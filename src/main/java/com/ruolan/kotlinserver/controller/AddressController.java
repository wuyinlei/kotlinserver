package com.ruolan.kotlinserver.controller;


import com.ruolan.kotlinserver.common.Constants;
import com.ruolan.kotlinserver.domain.AddShipAddressRequest;
import com.ruolan.kotlinserver.domain.DeleteShipAddressRequest;
import com.ruolan.kotlinserver.domain.ModifyShipAddressRequest;
import com.ruolan.kotlinserver.domain.base.BaseResponse;
import com.ruolan.kotlinserver.model.ShipAddress;
import com.ruolan.kotlinserver.service.ShipAddressService;
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
public class AddressController  {

    public static final int IS_DEFAULT = 0;
    public static final int IS_NOT_DEFAULT = 1;

    @Autowired
    private ShipAddressService shipAddressService;

    @RequestMapping(value = {"/getlist"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse<List<ShipAddress>> getList(HttpServletRequest request) {
        BaseResponse resp = new BaseResponse();
        List list = this.shipAddressService.getShipAddress(Integer.valueOf(request.getHeader(Constants.MESSAGE.USER_TOKEN)));
        if ((list == null) || (list.size() == 0)) {
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
    public BaseResponse add(HttpServletRequest request,@RequestBody AddShipAddressRequest req) {
        BaseResponse resp = new BaseResponse();
        ShipAddress address = new ShipAddress();
        address.setShipAddress(req.getShipAddress());
        address.setShipUserMobile(req.getShipUserMobile());
        address.setShipUserName(req.getShipUserName());
        address.setUserId(Integer.valueOf(request.getHeader(Constants.MESSAGE.USER_TOKEN)));
        List list = this.shipAddressService.getShipAddress(Integer.valueOf(request.getHeader(Constants.MESSAGE.USER_TOKEN)));
        if ((list == null) || (list.size() == 0))
            address.setShipIsDefault(Integer.valueOf(0));
        else {
            address.setShipIsDefault(Integer.valueOf(1));
        }
        this.shipAddressService.addShipAddress(address);
        resp.setStatus(Constants.CODE.SUCCESS_CODE);
        resp.setMessage(Constants.MESSAGE.ADD_ADDRESS_SUCCESS);
        return resp;
    }

    @RequestMapping(value = {"/modify"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse modify(HttpServletRequest request,@RequestBody ModifyShipAddressRequest req) {
        BaseResponse resp = new BaseResponse();
        ShipAddress address = new ShipAddress();
        address.setId(req.getId());
        address.setShipAddress(req.getShipAddress());
        address.setShipUserMobile(req.getShipUserMobile());
        address.setShipUserName(req.getShipUserName());
        address.setShipIsDefault(req.getShipIsDefault());

        if (req.getShipIsDefault().intValue() == 0) {
            for (ShipAddress shipAddress : this.shipAddressService.getShipAddress(Integer.valueOf(request.getHeader(Constants.MESSAGE.USER_TOKEN)))) {
                shipAddress.setShipIsDefault(Integer.valueOf(1));
                this.shipAddressService.modifyShipAddress(shipAddress);
            }

        }

        this.shipAddressService.modifyShipAddress(address);
        resp.setStatus(Constants.CODE.SUCCESS_CODE);
        resp.setMessage(Constants.MESSAGE.MODIFY_ADDRESS_SUCCESS);
        return resp;
    }

    @RequestMapping(value = {"/delete"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse delete(HttpServletRequest request,@RequestBody DeleteShipAddressRequest req) {
        BaseResponse resp = new BaseResponse();
        boolean isDefault = this.shipAddressService.getShipAddressById(req.getId()).getShipIsDefault().intValue() == 0;
        this.shipAddressService.deleteShipAddress(req.getId());
        List list = this.shipAddressService.getShipAddress(Integer.valueOf(request.getHeader(Constants.MESSAGE.USER_TOKEN)));
        if ((isDefault) && (list != null) && (list.size() > 0)) {
            ShipAddress address = (ShipAddress) list.get(0);
            address.setShipIsDefault(Integer.valueOf(0));
            this.shipAddressService.modifyShipAddress(address);
        }

        resp.setStatus(Constants.CODE.SUCCESS_CODE);
        resp.setMessage(Constants.MESSAGE.DELETE_ADDRESS_SUCCESS);
        return resp;
    }

}
