package com.example.vehiclerestapi.controller;

import com.example.vehiclerestapi.dto.ClientVehicleDetail;
import com.example.vehiclerestapi.dto.VehicleDetails;
import com.example.vehiclerestapi.exception.VehicleDetailsNotFound;
import com.example.vehiclerestapi.service.VehicleDetailService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
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

    int retryCount = 1;
    @GetMapping
    @CircuitBreaker(name = "getAllClientVehicleDetailsBreaker", fallbackMethod = "getAllClientVehicleDetailsFallback")
    @Retry(name="getAllClientVehicleDetails", fallbackMethod = "getAllClientVehicleDetailsFallback")
    @RateLimiter(name="getAllClientVehicleDetailsRateLimiter", fallbackMethod = "getAllClientVehicleDetailsFallback")
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<List<ClientVehicleDetail>> getAllClientVehicleDetailsController() {
        retryCount++;
        List<ClientVehicleDetail> dbVehicles = vehicleDetailService.getAllClientVehicleDetails();
        return new ResponseEntity<>(dbVehicles, HttpStatus.OK);
    }

    public ResponseEntity<List<ClientVehicleDetail>> getAllClientVehicleDetailsFallback(Exception ex) {
        return new ResponseEntity<>(faultClientVehicleDetails(), HttpStatus.OK);
    }

    @GetMapping("/{vehicleId}")
    //I don't need CircuitBreaker here because when it doesn't find anything according to my
    // given id then it will give us the vehicle not found exception
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<VehicleDetails> getVehicleById(@PathVariable int vehicleId) throws VehicleDetailsNotFound {
        return new ResponseEntity<>(vehicleDetailService.getVehicleById(vehicleId), HttpStatus.OK);
    }

    @GetMapping("/search")
    @CircuitBreaker(name="searchVehicleByFilterCriteriaBreaker", fallbackMethod = "searchVehicleByFilterCriteriaFallBackMethod")
    @Retry(name="searchVehicleByFilterCriteria", fallbackMethod = "searchVehicleByFilterCriteriaFallBackMethod")
    @RateLimiter(name="searchVehicleByFilterCriteriaRateLimiter", fallbackMethod = "searchVehicleByFilterCriteriaFallBackMethod")
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<List<ClientVehicleDetail>> searchVehicleByFilterCriteria(
            @RequestParam String modelYear, @RequestParam String brand, @RequestParam String model, @RequestParam String trim, @RequestParam String price) {
        List<ClientVehicleDetail> filteredClientVehicleDetailsList = vehicleDetailService.fetchVehicleDetailsByCriteria(modelYear, brand, model, trim, price);
        return new ResponseEntity<>(filteredClientVehicleDetailsList, HttpStatus.OK);
    }
    public ResponseEntity<List<ClientVehicleDetail>> searchVehicleByFilterCriteriaFallBackMethod(Exception ex){
        return new ResponseEntity<>(faultClientVehicleDetails(), HttpStatus.OK);
    }
    public List<ClientVehicleDetail> faultClientVehicleDetails(){
        return List.of(ClientVehicleDetail.builder()
                .modelYear(1234)
                .brandName("Dummy Brand")
                .modelName("Dummy Model")
                .trimType("Dummy TrimType")
                .bodyType("Dummy BodyType")
                .vehiclePrice(011561.2)
                .estimatedMonthlyPrice("$ 46161.146/monthly est.")
                .milesOnVehicle(64)
                .dealType("Dummy dealType")
                .amountBelowMarketPrice("$ 4545656 below market price.")
                .interestRate(2.5)
                .locationOfVehicle("Dummy Location")
                .sellerName("hjsdksdfs")
                .sellerContactNumber("+454564.1").build());
    }
}
