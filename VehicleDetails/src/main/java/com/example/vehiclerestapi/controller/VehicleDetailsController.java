package com.example.vehiclerestapi.controller;

import com.example.vehiclerestapi.dto.VehicleDetailsDTO;
import com.example.vehiclerestapi.entity.VehicleDetails;
import com.example.vehiclerestapi.exception.MandatoryFieldMissingException;
import com.example.vehiclerestapi.exception.VehicleDetailsNotFound;
import com.example.vehiclerestapi.service.VehicleDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicle-details")
public class VehicleDetailsController {
    @Autowired
    VehicleDetailsService vehicleDetailsService;

    @PostMapping
    public ResponseEntity<VehicleDetails> saveVehicle(@Valid @RequestBody VehicleDetails vehicleDetails, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            String allErrors = "";
            for (ObjectError err : errorList) {
                allErrors += err.getDefaultMessage() + ",";
            }
            throw new MandatoryFieldMissingException(allErrors);
        }
        VehicleDetails saveVehicleInfo = vehicleDetailsService.saveVehicleDetails(vehicleDetails);
        return new ResponseEntity<>(saveVehicleInfo, HttpStatus.CREATED);
    }

    @GetMapping
    public VehicleDetailsDTO getAllVehicleDetails() throws VehicleDetailsNotFound {
        List<VehicleDetails> savedVehicle = vehicleDetailsService.fetchAllVehicleDetails();
        return new VehicleDetailsDTO(savedVehicle);
    }

    @GetMapping("/{vehicleId}")
    public VehicleDetails getVehicleDetailsById(@PathVariable int vehicleId) throws VehicleDetailsNotFound {
        return vehicleDetailsService.getVehicleById(vehicleId);
    }

    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<String> deleteVehicleDetailsById(@PathVariable int vehicleId) throws VehicleDetailsNotFound {
        vehicleDetailsService.deleteVehicleDetailsById(vehicleId);
        return new ResponseEntity<>("Delete vehicle details from DB with ID- " + vehicleId, HttpStatus.OK);
    }

    @PutMapping("/{vehicleId}")
    public ResponseEntity<VehicleDetails> updateVehicleDetailsUsingId(@PathVariable int vehicleId, @RequestBody VehicleDetails vehicleDetails) throws VehicleDetailsNotFound {
        VehicleDetails updatedVehicleDetails = vehicleDetailsService.updateVehicleDetails(vehicleId, vehicleDetails);
        return new ResponseEntity<>(updatedVehicleDetails, HttpStatus.OK);
    }

    @GetMapping("/search")
    public VehicleDetailsDTO getVehicleDetailsByCriteria(@RequestParam String modelYear, @RequestParam String brand, @RequestParam String model, @RequestParam String trim, @RequestParam String price) throws VehicleDetailsNotFound {
        if(price =="" || price==null){
            price="0.0";
        }
        if(modelYear==""||modelYear==null){
            modelYear="0";
        }
        List<VehicleDetails> filteredVehicles = vehicleDetailsService.fetchFilteredVehicleDetails(Integer.parseInt(modelYear), brand, model, trim, Double.parseDouble(price));
        return new VehicleDetailsDTO(filteredVehicles);
    }
}
