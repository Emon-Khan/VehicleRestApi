package com.example.vehiclerestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientVehicleDetail {
    private int id;
    private int modelYear;
    private String brandName;
    private String modelName;
    private String trimType;
    private String bodyType;
    private double vehiclePrice;
    private String estimatedMonthlyPrice;
    private int milesOnVehicle;
    private String dealType;
    private String amountBelowMarketPrice;
    private double interestRate;
    private String locationOfVehicle;
    private String sellerName;
    private String sellerContactNumber;
}
