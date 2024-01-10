package com.example.vehiclerestapi.service.impl;

import com.example.vehiclerestapi.dao.VehicleMarketPriceDAO;
import com.example.vehiclerestapi.entity.VehicleMarketPrice;
import com.example.vehiclerestapi.service.VehicleMarketPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleMarketPriceServiceImpl implements VehicleMarketPriceService {
    @Autowired
    VehicleMarketPriceDAO vehicleMarketPriceDAO;
    @Override
    public VehicleMarketPrice saveVehicleMarketPrice(VehicleMarketPrice vehicleMarketPrice){
        return vehicleMarketPriceDAO.save(vehicleMarketPrice);
    }
    @Override
    public VehicleMarketPrice getVehicleMarketPriceByBrandAndModelAndTrimAndYear(String brandName, String modelName, String trimType, int modelYear) {
        return vehicleMarketPriceDAO.findByBrandNameAndModelNameAndTrimTypeAndModelYear(brandName, modelName, trimType, modelYear).get();
    }
}
