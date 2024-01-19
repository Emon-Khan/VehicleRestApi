package com.example.vehiclerestapi.service.impl;

import com.example.vehiclerestapi.dao.VehicleMarketPriceDAO;
import com.example.vehiclerestapi.dto.ClientVehicleDetail;
import com.example.vehiclerestapi.dto.VehicleDetails;
import com.example.vehiclerestapi.dto.VehicleDetailsDTO;
import com.example.vehiclerestapi.entity.VehicleMarketPrice;
import com.example.vehiclerestapi.exception.VehicleDetailsNotFound;
import com.example.vehiclerestapi.exception.VehicleMarketPriceNotFoundException;
import com.example.vehiclerestapi.service.VehicleDetailService;
import com.example.vehiclerestapi.service.VehicleMarketPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VehicleDetailServiceImpl implements VehicleDetailService {
    @Autowired
    public RestTemplate restTemplate;
    @Autowired
    public VehicleMarketPriceService vehicleMarketPriceService;
    @Autowired
    public VehicleMarketPriceDAO vehicleMarketPriceDAO;

    @Override
    public List<ClientVehicleDetail> getAllClientVehicleDetails() {
        VehicleDetailsDTO vehicleDetailsDTO = restTemplate.getForObject(
                "http://VEHICLE-DETAILS-API/api/v1/vehicle-details", VehicleDetailsDTO.class);
        List<ClientVehicleDetail> clientVehicleDetailsList = vehicleDetailsDTO.getVehicleDetailsList().stream()
                .map(vehicle -> {
                    try {
                        return mapClientVehicleDetailFromVehicleDetail(vehicle);
                    } catch (VehicleMarketPriceNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
        return clientVehicleDetailsList;
    }

    @Override
    public VehicleDetails getVehicleById(int vehicleId) throws VehicleDetailsNotFound {
        VehicleDetails dbVehicle = null;
        try {
            dbVehicle = restTemplate.getForObject("http://VEHICLE-DETAILS-API/api/v1/vehicle-details/" + vehicleId, VehicleDetails.class);
        } catch (Exception ex) {
            throw new VehicleDetailsNotFound("No Vehicle is found with this id " + vehicleId);
        }
        return dbVehicle;
    }

    @Override
    public List<ClientVehicleDetail> fetchVehicleDetailsByCriteria(String modelYear, String brand, String model, String trim, String price) {
        Map<String, String> params = new HashMap<>();
        params.put("modelYear", modelYear);
        params.put("brand", brand);
        params.put("model", model);
        params.put("trim", trim);
        params.put("price", price);
        String url = "http://VEHICLE-DETAILS-API/api/v1/vehicle-details/search?modelYear={modelYear}&brand={brand}&model={model}&trim={trim}&price={price}";
        VehicleDetailsDTO filteredList = restTemplate.getForObject(url, VehicleDetailsDTO.class, params);
        return filteredList.getVehicleDetailsList().stream().map(vehicleDetails -> {
            try {
                return mapClientVehicleDetailFromVehicleDetail(vehicleDetails);
            } catch (VehicleMarketPriceNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    private ClientVehicleDetail mapClientVehicleDetailFromVehicleDetail(VehicleDetails vehicleDetails)
            throws VehicleMarketPriceNotFoundException {
        ClientVehicleDetail clientVehicleDetail = new ClientVehicleDetail();
        clientVehicleDetail.setId(vehicleDetails.getId());
        clientVehicleDetail.setModelYear(vehicleDetails.getModelYear());
        clientVehicleDetail.setBrandName(vehicleDetails.getBrandName());
        clientVehicleDetail.setModelName(vehicleDetails.getModelName());
        clientVehicleDetail.setTrimType(vehicleDetails.getTrimType());
        clientVehicleDetail.setBodyType(vehicleDetails.getBodyType());
        clientVehicleDetail.setVehiclePrice(vehicleDetails.getVehiclePrice());
        clientVehicleDetail.setMilesOnVehicle(vehicleDetails.getMilesOnVehicle());
        clientVehicleDetail.setLocationOfVehicle(vehicleDetails.getLocationOfVehicle());
        clientVehicleDetail.setSellerName(vehicleDetails.getSellerName());
        clientVehicleDetail.setSellerContactNumber(vehicleDetails.getSellerContactNumber());
        clientVehicleDetail.setInterestRate(vehicleDetails.getInterestRate());

        //calculated estimated monthly price
        //price/(5*12) + price*interest_rate/(100*12) Because interest
        // rate has been given for a year and here 5 is indication years
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double monthlyPrice = monthlyPriceCalculation(vehicleDetails);
        double totalAmmountToBePaid = monthlyPrice * 10 * 12;
        //$577.31/monthly est.
        clientVehicleDetail.setEstimatedMonthlyPrice("$" + decimalFormat.format(monthlyPrice) + "/monthly est.");
        //Calculate amount below or above market price
        //Calculate current market price
        //Market Price (New Vehicle) - (Current year -model year)*market price * 0.5/25-current miles*market price*75/500000
        VehicleMarketPrice dbMarketPriceBasedOnBrandAndModelAndTrimAndYear = vehicleMarketPriceDAO.findByBrandNameAndModelNameAndTrimTypeAndModelYear(vehicleDetails.getBrandName(), vehicleDetails.getModelName(), vehicleDetails.getTrimType(), vehicleDetails.getModelYear()).orElseThrow(
                () ->  new VehicleMarketPriceNotFoundException("Vehicle Market Price not found for this BrandName, ModelName, TrimType & Year" +
                vehicleDetails.getBrandName() + " & " + vehicleDetails.getModelName() + " & " + vehicleDetails.getTrimType() + " & " + vehicleDetails.getModelYear()
        ));
        double currentVehicleMarketPrice = dbMarketPriceBasedOnBrandAndModelAndTrimAndYear.getPrice();
        double marketPriceComparison = currentVehicleMarketPrice - totalAmmountToBePaid;
        if (marketPriceComparison > 0) {
            clientVehicleDetail.setAmountBelowMarketPrice("$" + marketPriceComparison + " below market price.");
        } else {
            clientVehicleDetail.setAmountBelowMarketPrice("$" + Math.abs(marketPriceComparison) + " above market price.");
        }
        //deal type determination
        if (marketPriceComparison > 8000) {
            clientVehicleDetail.setDealType("Great Deal");
        } else if (marketPriceComparison > 6000 && marketPriceComparison <= 8000) {
            clientVehicleDetail.setDealType("Good Deal");
        } else if (marketPriceComparison > 3000 && marketPriceComparison <= 6000) {
            clientVehicleDetail.setDealType("Fair Deal");
        } else {
            clientVehicleDetail.setDealType("Bad Deal");
        }
        return clientVehicleDetail;
    }

    private double monthlyPriceCalculation(VehicleDetails vehicleDetails) {
        double interestRateMonthly = vehicleDetails.getInterestRate() / (12 * 100);
        double numberOfMonth = 10 * 12;
        double calculation1 = Math.pow((1.0 + interestRateMonthly), numberOfMonth);
        double monthlyPrice = (vehicleDetails.getVehiclePrice() * interestRateMonthly * calculation1) / (calculation1 - 1); //compound interest rule
        return monthlyPrice;
    }

}
