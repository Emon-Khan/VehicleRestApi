package com.example.vehiclerestapi.controller;

import com.example.vehiclerestapi.dto.ClientVehicleDetail;
import com.example.vehiclerestapi.dto.VehicleDetails;
import com.example.vehiclerestapi.exception.VehicleDetailsNotFound;
import com.example.vehiclerestapi.service.VehicleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicle-search/vehicle-details")
public class VehicleDetailsController {
    @Autowired
    private VehicleDetailService vehicleDetailService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<List<ClientVehicleDetail>> getAllClientVehicleDetailsController() {
        List<ClientVehicleDetail> dbVehicles = vehicleDetailService.getAllClientVehicleDetails();
        return new ResponseEntity<>(dbVehicles, HttpStatus.OK);
    }

    @GetMapping("/{vehicleId}")
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<VehicleDetails> getVehicleById(@PathVariable int vehicleId) throws VehicleDetailsNotFound {
        return new ResponseEntity<>(vehicleDetailService.getVehicleById(vehicleId), HttpStatus.OK);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<List<ClientVehicleDetail>> searchVehicleByFilterCriteria(@RequestParam String modelYear, @RequestParam String brand, @RequestParam String model, @RequestParam String trim, @RequestParam String price) {
        List<ClientVehicleDetail> filteredClientVehicleDetailsList = vehicleDetailService.fetchVehicleDetailsByCriteria(modelYear, brand, model, trim, price);
        return new ResponseEntity<>(filteredClientVehicleDetailsList, HttpStatus.OK);
    }
}
