package com.example.vehiclerestapi.service;

import com.example.vehiclerestapi.entity.Manufacturer;
import com.example.vehiclerestapi.exception.ManufacturerNotFoundException;

import java.util.List;

public interface ManufacturerService {
    Manufacturer saveManufacturer(Manufacturer manufacturer);
    List<Manufacturer> fetchAllManufacturers();
    Manufacturer getManufacturerById(int id);
    Manufacturer updateManufacturer(int id, Manufacturer updatedManufacturer);
    void deleteManufacturerByID(int id) throws ManufacturerNotFoundException;
}
