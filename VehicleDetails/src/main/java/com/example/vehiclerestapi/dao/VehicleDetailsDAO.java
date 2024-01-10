package com.example.vehiclerestapi.dao;

import com.example.vehiclerestapi.entity.VehicleDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VehicleDetailsDAO extends JpaRepository<VehicleDetails, Integer> {
    @Query(value = "SELECT * FROM vehicle_details where model_year<=?1 and brand_name=?2 and model_name=?3 and trim_type=?4 and vehicle_price<=?5", nativeQuery = true)
    List<VehicleDetails> filterVehicleBasedOnCriteria(int modelYear, String brand, String model, String trim, double price);
    @Query(value = "SELECT * FROM vehicle_details where model_year<=?1 and brand_name=?2 and model_name=?3 and trim_type=?4", nativeQuery = true)
    List<VehicleDetails> filterVehicleBasedOnCriteria5(int modelYear, String brand, String model, String trim);
    @Query(value = "SELECT * FROM vehicle_details where model_year<=?1 and brand_name=?2 and model_name=?3 and vehicle_price<=?4", nativeQuery = true)
    List<VehicleDetails> filterVehicleBasedOnCriteria6(int modelYear, String brand, String model, double price);
    @Query(value = "SELECT * FROM vehicle_details where model_year<=?1 and brand_name=?2 and vehicle_price<=?3", nativeQuery = true)
    List<VehicleDetails> filterVehicleBasedOnCriteria7(int modelYear, String brand, double price);
    @Query(value = "SELECT * FROM vehicle_details where model_year<=?1 and vehicle_price<=?2", nativeQuery = true)
    List<VehicleDetails> filterVehicleBasedOnCriteria8(int modelYear, double price);
    @Query(value = "SELECT * FROM vehicle_details where model_year<=?1", nativeQuery = true)
    List<VehicleDetails> filterVehicleBasedOnCriteria9(int modelYear);
    @Query(value = "SELECT * FROM vehicle_details where brand_name=?1 and model_name=?2 and trim_type=?3 and vehicle_price<=?4", nativeQuery = true)
    List<VehicleDetails> filterVehicleBasedOnCriteria(String brand, String model, String trim, double price);
    @Query(value = "SELECT * FROM vehicle_details where brand_name=?1 and model_name=?2 and trim_type=?3", nativeQuery = true)
    List<VehicleDetails> filterVehicleBasedOnCriteria1(String brand, String model, String trim);
    @Query(value = "SELECT * FROM vehicle_details where brand_name=?1 and model_name=?2 and vehicle_price<=?3", nativeQuery = true)
    List<VehicleDetails> filterVehicleBasedOnCriteria2(String brand, String model, double price);
    @Query(value = "SELECT * FROM vehicle_details where brand_name=?1 and vehicle_price<=?2", nativeQuery = true)
    List<VehicleDetails> filterVehicleBasedOnCriteria2(String brand, double price);
    @Query(value = "SELECT * FROM vehicle_details where brand_name=?1", nativeQuery = true)
    List<VehicleDetails> filterVehicleBasedOnCriteria3(String brand);
    @Query(value = "SELECT * FROM vehicle_details where vehicle_price<=?1", nativeQuery = true)
    List<VehicleDetails> filterVehicleBasedOnCriteria4(double price);
}
