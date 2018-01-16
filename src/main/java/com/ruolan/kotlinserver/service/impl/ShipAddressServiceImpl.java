package com.ruolan.kotlinserver.service.impl;

import com.ruolan.kotlinserver.dao.ShipAddressDao;
import com.ruolan.kotlinserver.model.ShipAddress;
import com.ruolan.kotlinserver.service.ShipAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipAddressServiceImpl implements ShipAddressService {

    @Autowired
    private ShipAddressDao addressDao;


    @Override
    public int addShipAddress(ShipAddress paramShipAddress) {
        return addressDao.insert(paramShipAddress);
    }

    @Override
    public List<ShipAddress> getShipAddress(Integer userId) {
        return addressDao.selectShipAddressByUserId(userId);
    }

    @Override
    public int modifyShipAddress(ShipAddress paramShipAddress) {
        return addressDao.updateByPrimaryKey(paramShipAddress);
    }

    @Override
    public int deleteShipAddress(Integer userId) {
        return addressDao.deleteByPrimaryKey(userId);
    }

    @Override
    public ShipAddress getShipAddressById(Integer userId) {
        return addressDao.selectByPrimaryKey(userId);
    }
}
