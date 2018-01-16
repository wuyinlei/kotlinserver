package com.ruolan.kotlinserver.dao;

import com.ruolan.kotlinserver.model.ShipAddress;

import java.util.List;

public interface ShipAddressDao {


    int deleteByPrimaryKey(Integer paramInteger);

    int insert(ShipAddress paramShipAddress);

    int insertSelective(ShipAddress paramShipAddress);

    ShipAddress selectByPrimaryKey(Integer paramInteger);

    int updateByPrimaryKeySelective(ShipAddress paramShipAddress);

    int updateByPrimaryKey(ShipAddress paramShipAddress);

    List<ShipAddress> selectShipAddressByUserId(Integer paramInteger);

}
