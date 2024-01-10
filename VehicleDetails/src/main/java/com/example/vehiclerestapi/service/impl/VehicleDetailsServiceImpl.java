package com.example.vehiclerestapi.service.impl;

import com.example.vehiclerestapi.dao.VehicleDetailsDAO;
import com.example.vehiclerestapi.entity.VehicleDetails;
import com.example.vehiclerestapi.exception.VehicleDetailsNotFound;
import com.example.vehiclerestapi.exception.VehicleNotSaved;
import com.example.vehiclerestapi.service.VehicleDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VehicleDetailsServiceImpl implements VehicleDetailsService {
    @Autowired
    private VehicleDetailsDAO vehicleDetailsDao;

    @Override
    public VehicleDetails saveVehicleDetails(VehicleDetails vehicleDetails) throws VehicleNotSaved {
        VehicleDetails dbVehicleDetails = null;
        try {
            dbVehicleDetails = vehicleDetailsDao.save(vehicleDetails);
        } catch (Exception ex) {
            throw new VehicleNotSaved("Unable to save vehicle in DB.Got error " + ex.getMessage());
        }
        return dbVehicleDetails;
    }

    @Override
    public List<VehicleDetails> fetchAllVehicleDetails() throws VehicleDetailsNotFound {
        List<VehicleDetails> dbVehicles = vehicleDetailsDao.findAll();
        if (dbVehicles.size() == 0) {
            throw new VehicleDetailsNotFound("No vehicle details found in Database!");
        }
        return dbVehicles;
    }

    @Override
    public VehicleDetails getVehicleById(int vehicleId) throws VehicleDetailsNotFound {
        Optional<VehicleDetails> optionalVehicleDetails = vehicleDetailsDao.findById(vehicleId);
        if (!optionalVehicleDetails.isPresent()) {
            throw new VehicleDetailsNotFound("No vehicle details found in database for vehicle ID-" + vehicleId);
        }
        return optionalVehicleDetails.get();
    }

    @Override
    public void deleteVehicleDetailsById(int vehicleId) throws VehicleDetailsNotFound {
        Optional<VehicleDetails> optionalVehicleDetails = vehicleDetailsDao.findById(vehicleId);
        if (!optionalVehicleDetails.isPresent()) {
            throw new VehicleDetailsNotFound("No vehicle details found in database for vehicle ID-" + vehicleId);
        }
        vehicleDetailsDao.deleteById(vehicleId);
    }

    @Override
    public VehicleDetails updateVehicleDetails(int vehicleId, VehicleDetails vehicleDetails) throws VehicleDetailsNotFound {
        Optional<VehicleDetails> optionalVehicleDetails = vehicleDetailsDao.findById(vehicleId);
        if (!optionalVehicleDetails.isPresent()) {
            throw new VehicleDetailsNotFound("No vehicle details found in database for vehicle ID-" + vehicleId);
        }
        VehicleDetails dbVehicleDetails = optionalVehicleDetails.get();
        if (vehicleDetails.getModelYear() != 0 && Objects.nonNull(vehicleDetails.getModelYear())) {
            dbVehicleDetails.setModelYear(vehicleDetails.getModelYear());
        }
        if (vehicleDetails.getBrandName() != "" && Objects.nonNull(vehicleDetails.getBrandName())) {
            dbVehicleDetails.setBrandName(vehicleDetails.getBrandName());
        }
        if (vehicleDetails.getModelName() != "" && Objects.nonNull(vehicleDetails.getModelName())) {
            dbVehicleDetails.setModelName(vehicleDetails.getModelName());
        }
        if (vehicleDetails.getVehiclePrice() != 0.0 && Objects.nonNull(vehicleDetails.getVehiclePrice())) {
            dbVehicleDetails.setVehiclePrice(vehicleDetails.getVehiclePrice());
        }
        if (vehicleDetails.getMilesOnVehicle() != 0 && Objects.nonNull(vehicleDetails.getMilesOnVehicle())) {
            dbVehicleDetails.setMilesOnVehicle(vehicleDetails.getMilesOnVehicle());
        }
        if (vehicleDetails.getInterestRate() != 0.0 && Objects.nonNull(vehicleDetails.getInterestRate())) {
            dbVehicleDetails.setInterestRate(vehicleDetails.getInterestRate());
        }
        if (vehicleDetails.getSellerName() != "" && Objects.nonNull(vehicleDetails.getSellerName())) {
            dbVehicleDetails.setSellerName(vehicleDetails.getSellerName());
        }
        if (vehicleDetails.getSellerContactNumber() != "" && Objects.nonNull(vehicleDetails.getSellerContactNumber())) {
            dbVehicleDetails.setSellerContactNumber(vehicleDetails.getSellerContactNumber());
        }
        dbVehicleDetails.setTrimType(vehicleDetails.getTrimType());
        dbVehicleDetails.setBodyType(vehicleDetails.getBodyType());
        dbVehicleDetails.setLocationOfVehicle(vehicleDetails.getLocationOfVehicle());
        dbVehicleDetails.setVehicleDescription(vehicleDetails.getVehicleDescription());
        vehicleDetailsDao.save(dbVehicleDetails);
        return dbVehicleDetails;
    }

    @Override
    public List<VehicleDetails> fetchFilteredVehicleDetails(int modelYear, String brand, String model, String trim, double price) throws VehicleDetailsNotFound {
        List<VehicleDetails> vehicleDetailsList = null;
        if (modelYear > 1900 && brand != "" && model != "" && trim != "" && price > 0.0) {
            vehicleDetailsList = vehicleDetailsDao.filterVehicleBasedOnCriteria(modelYear, brand, model, trim, price);
        } else if (modelYear > 1900 && brand != "" && model != "" && trim != "") {
            vehicleDetailsList = vehicleDetailsDao.filterVehicleBasedOnCriteria5(modelYear, brand, model, trim);
        } else if (modelYear > 1900 && brand != "" && model != "" && price > 0.0) {
            vehicleDetailsList = vehicleDetailsDao.filterVehicleBasedOnCriteria6(modelYear, brand, model, price);
        } else if (modelYear > 1900 && brand != "" && price > 0.0) {
            vehicleDetailsList = vehicleDetailsDao.filterVehicleBasedOnCriteria7(modelYear, brand, price);
        } else if (modelYear > 1900 && price > 0.0) {
            vehicleDetailsList = vehicleDetailsDao.filterVehicleBasedOnCriteria8(modelYear, price);
        } else if (modelYear > 1900) {
            vehicleDetailsList = vehicleDetailsDao.filterVehicleBasedOnCriteria9(modelYear);
        } else if (brand != "" && model != "" && trim != "" && price > 0.0) {
            vehicleDetailsList = vehicleDetailsDao.filterVehicleBasedOnCriteria(brand, model, trim, price);
        } else if (brand != "" && model != "" && trim != "") {
            vehicleDetailsList = vehicleDetailsDao.filterVehicleBasedOnCriteria1(brand, model, trim);
        } else if (brand != "" && model != "" && price > 0.0) {
            vehicleDetailsList = vehicleDetailsDao.filterVehicleBasedOnCriteria2(brand, model, price);
        } else if (brand != "" && price > 0.0) {
            vehicleDetailsList = vehicleDetailsDao.filterVehicleBasedOnCriteria2(brand, price);
        } else if (brand != "") {
            vehicleDetailsList = vehicleDetailsDao.filterVehicleBasedOnCriteria3(brand);
        } else if (price > 0.0) {
            vehicleDetailsList = vehicleDetailsDao.filterVehicleBasedOnCriteria4(price);
        } else {
            vehicleDetailsList = fetchAllVehicleDetails();
        }
        return vehicleDetailsList;
    }
}
