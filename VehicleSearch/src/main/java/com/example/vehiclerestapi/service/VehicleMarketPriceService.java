package com.example.vehiclerestapi.service;

import com.example.vehiclerestapi.entity.VehicleMarketPrice;

public interface VehicleMarketPriceService {
    VehicleMarketPrice saveVehicleMarketPrice(VehicleMarketPrice vehicleMarketPrice);
    VehicleMarketPrice getVehicleMarketPriceByBrandAndModelAndTrimAndYear(String brandName, String modelName, String trimType, int modelYear);
}
