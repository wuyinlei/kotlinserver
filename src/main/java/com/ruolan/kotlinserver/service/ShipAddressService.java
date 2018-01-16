package com.ruolan.kotlinserver.service;

import com.ruolan.kotlinserver.model.ShipAddress;

import java.util.List;

public interface ShipAddressService {

    int addShipAddress(ShipAddress paramShipAddress);

    List<ShipAddress> getShipAddress(Integer paramInteger);

    int modifyShipAddress(ShipAddress paramShipAddress);

    int deleteShipAddress(Integer paramInteger);

    ShipAddress getShipAddressById(Integer paramInteger);

}
