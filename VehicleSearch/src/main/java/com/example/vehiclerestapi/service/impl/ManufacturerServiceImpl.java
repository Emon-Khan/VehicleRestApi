package com.example.vehiclerestapi.service.impl;

import com.example.vehiclerestapi.dao.ManufacturerDAO;
import com.example.vehiclerestapi.entity.Manufacturer;
import com.example.vehiclerestapi.exception.ManufacturerNotFoundException;
import com.example.vehiclerestapi.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    @Autowired
    private ManufacturerDAO manufacturerDAO;

    @Override
    public Manufacturer saveManufacturer(Manufacturer manufacturer) {
        return manufacturerDAO.save(manufacturer);
    }

    @Override
    public List<Manufacturer> fetchAllManufacturers() {
        List<Manufacturer> allManufacturer = manufacturerDAO.findAll();
        return allManufacturer;
    }

    @Override
    public Manufacturer getManufacturerById(int id) {
        Optional<Manufacturer> manufacturerFromDbUsingId = manufacturerDAO.findById(id);
        if (manufacturerFromDbUsingId.isPresent()) {
            return manufacturerFromDbUsingId.get();
        }
        return null;
    }

    @Override
    public Manufacturer updateManufacturer(int id, Manufacturer updatedManufacturer) {
        Manufacturer manufacturerBasedOnId = getManufacturerById(id);
        if (manufacturerBasedOnId != null && Objects.nonNull(updatedManufacturer)) {
            if (!"".equalsIgnoreCase(updatedManufacturer.getManufacturerName())) {
                manufacturerBasedOnId.setManufacturerName(updatedManufacturer.getManufacturerName());
            }
            if (!"".equalsIgnoreCase(updatedManufacturer.getCountryOfOrigin())) {
                manufacturerBasedOnId.setCountryOfOrigin((updatedManufacturer.getCountryOfOrigin()));
            }
            return manufacturerDAO.save(manufacturerBasedOnId);
        }
        return manufacturerBasedOnId;
    }

    @Override
    public void deleteManufacturerByID(int id) throws ManufacturerNotFoundException{
        Manufacturer manufacturerBasedOnID = getManufacturerById(id);
        if (manufacturerBasedOnID == null) {
            throw new ManufacturerNotFoundException("Manufacturer not found for ID- "+id);
        }
        manufacturerDAO.deleteById(id);
    }
}
