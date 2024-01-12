package com.example.vehiclerestapi.dao;

import com.example.vehiclerestapi.entity.TrimType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrimTypeDAO extends JpaRepository<TrimType, Integer> {
}
