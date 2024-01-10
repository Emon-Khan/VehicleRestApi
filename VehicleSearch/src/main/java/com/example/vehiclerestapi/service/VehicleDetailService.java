package com.example.vehiclerestapi.service;

import com.example.vehiclerestapi.dto.ClientVehicleDetail;
import com.example.vehiclerestapi.dto.VehicleDetails;
import com.example.vehiclerestapi.exception.VehicleDetailsNotFound;

import java.util.List;

public interface VehicleDetailService {
    List<ClientVehicleDetail> getAllClientVehicleDetails();
    VehicleDetails getVehicleById(int vehicleId) throws VehicleDetailsNotFound;
    List<ClientVehicleDetail> fetchVehicleDetailsByCriteria(String modelYear, String brand, String model, String trim, String price);
}
