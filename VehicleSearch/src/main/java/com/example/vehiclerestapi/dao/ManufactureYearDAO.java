package com.example.vehiclerestapi.dao;

import com.example.vehiclerestapi.entity.ManufacturerYear;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufactureYearDAO extends JpaRepository<ManufacturerYear, Integer> {
}
