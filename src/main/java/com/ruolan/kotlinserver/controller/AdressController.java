package com.ruolan.kotlinserver.controller;


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

import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping(produces = {"application/json;charset=UTF-8"}, value = {"/address"})
public class AdressController extends BaseController{

    public static final int IS_DEFAULT = 0;
    public static final int IS_NOT_DEFAULT = 1;

    @Autowired
    private ShipAddressService shipAddressService;

    @RequestMapping(value = {"/getlist"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse<List<ShipAddress>> getList() {
        BaseResponse resp = new BaseResponse();

        List list = this.shipAddressService.getShipAddress(Integer.valueOf(this.request.getHeader("token")));
        if ((list == null) || (list.size() == 0)) {
            resp.setStatus(0);
            resp.setMessage("地址列表为空");
            return resp;
        }

        resp.setStatus(0);
        resp.setMessage("分类列表获取成功");
        resp.setData(list);
        return resp;
    }

    @RequestMapping(value = {"/add"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse add(@RequestBody AddShipAddressRequest req) {
        BaseResponse resp = new BaseResponse();

        ShipAddress address = new ShipAddress();
        address.setShipAddress(req.getShipAddress());
        address.setShipUserMobile(req.getShipUserMobile());
        address.setShipUserName(req.getShipUserName());
        address.setUserId(Integer.valueOf(this.request.getHeader("token")));
        List list = this.shipAddressService.getShipAddress(Integer.valueOf(this.request.getHeader("token")));
        if ((list == null) || (list.size() == 0))
            address.setShipIsDefault(Integer.valueOf(0));
        else {
            address.setShipIsDefault(Integer.valueOf(1));
        }
        this.shipAddressService.addShipAddress(address);
        resp.setStatus(0);
        resp.setMessage("添加地址成功");
        return resp;
    }

    @RequestMapping(value = {"/modify"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse modify(@RequestBody ModifyShipAddressRequest req) {
        BaseResponse resp = new BaseResponse();

        ShipAddress address = new ShipAddress();
        address.setId(req.getId());
        address.setShipAddress(req.getShipAddress());
        address.setShipUserMobile(req.getShipUserMobile());
        address.setShipUserName(req.getShipUserName());
        address.setShipIsDefault(req.getShipIsDefault());

        if (req.getShipIsDefault().intValue() == 0) {
            for (ShipAddress shipAddress : this.shipAddressService.getShipAddress(Integer.valueOf(this.request.getHeader("token")))) {
                shipAddress.setShipIsDefault(Integer.valueOf(1));
                this.shipAddressService.modifyShipAddress(shipAddress);
            }

        }

        this.shipAddressService.modifyShipAddress(address);
        resp.setStatus(0);
        resp.setMessage("修改成功");
        return resp;
    }

    @RequestMapping(value = {"/delete"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public BaseResponse delete(@RequestBody DeleteShipAddressRequest req) {
        BaseResponse resp = new BaseResponse();

        boolean isDefault = this.shipAddressService.getShipAddressById(req.getId()).getShipIsDefault().intValue() == 0;
        this.shipAddressService.deleteShipAddress(req.getId());
        List list = this.shipAddressService.getShipAddress(Integer.valueOf(this.request.getHeader("token")));
        if ((isDefault) && (list != null) && (list.size() > 0)) {
            ShipAddress address = (ShipAddress) list.get(0);
            address.setShipIsDefault(Integer.valueOf(0));
            this.shipAddressService.modifyShipAddress(address);
        }

        resp.setStatus(0);
        resp.setMessage("刪除成功");
        return resp;
    }

}
