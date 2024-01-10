package com.example.vehiclerestapi.dao;

import com.example.vehiclerestapi.entity.VehicleMarketPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleMarketPriceDAO extends JpaRepository<VehicleMarketPrice, Integer> {
    Optional<VehicleMarketPrice> findByBrandNameAndModelNameAndTrimTypeAndModelYear(String brandName, String modelName, String trimType, int modelYear);
}
