package com.example.vehiclerestapi.dao;

import com.example.vehiclerestapi.entity.Manufacturer;
import com.example.vehiclerestapi.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelDAO extends JpaRepository<Model, Integer> {
    List<Model> findByManufacturer(Manufacturer manufacturer);
}
