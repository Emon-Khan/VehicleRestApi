package com.example.vehiclerestapi.controller;

import com.example.vehiclerestapi.entity.VehicleMarketPrice;
import com.example.vehiclerestapi.service.VehicleMarketPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vehicle-search/vehicle-market-price")
public class VehicleMarketPriceController {
    @Autowired
    private VehicleMarketPriceService vehicleMarketPriceService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin:create', 'user:create')")
    public ResponseEntity<VehicleMarketPrice> saveVehicleMarketPrice(@RequestBody VehicleMarketPrice vehicleMarketPrice) {
        VehicleMarketPrice saveVehicleMarketPrice = vehicleMarketPriceService.saveVehicleMarketPrice(vehicleMarketPrice);
        return new ResponseEntity<>(saveVehicleMarketPrice, HttpStatus.CREATED);
    }
}
