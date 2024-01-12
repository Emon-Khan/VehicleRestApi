package com.example.vehiclerestapi.dao;

import com.example.vehiclerestapi.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerDAO extends JpaRepository<Manufacturer, Integer> {
    Manufacturer findByManufacturerName(String name);
}
