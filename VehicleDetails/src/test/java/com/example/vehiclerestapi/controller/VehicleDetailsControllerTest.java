package com.example.vehiclerestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.vehiclerestapi.dao.VehicleDetailsDAO;
import com.example.vehiclerestapi.entity.VehicleDetails;
import com.example.vehiclerestapi.exception.VehicleDetailsNotFound;
import com.example.vehiclerestapi.service.VehicleDetailsService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = VehicleDetailsController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class VehicleDetailsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VehicleDetailsService vehicleDetailsService;
    @Autowired
    private ObjectMapper objectMapper;

    private VehicleDetails vehicleDetails;
    private VehicleDetailsDAO vehicleDetailsDAO;

    @BeforeEach
    public void init() {
        vehicleDetails = VehicleDetails.builder()
                .modelYear(2020)
                .brandName("Volkswagen")
                .modelName("Arteon")
                .trimType("SE")
                .bodyType("Sedan")
                .vehiclePrice(64496.759)
                .milesOnVehicle(1500)
                .interestRate(2.16)
                .locationOfVehicle("Perth, Australia")
                .vehicleDescription("The Volkswagen Arteon is a car manufactured by German car manufacturer Volkswagen.\n" +
                        "Described as a large family car or a mid-size car, it is available in five-door \n" +
                        "liftback or estate body styles.")
                .sellerName("Nexus Motors Ltd")
                .sellerContactNumber("+880185365127").build();
    }

    @Test
    public void VehicleDetailsController_CreateVehicle_ReturnCreated() throws Exception {
        given(vehicleDetailsService.saveVehicleDetails(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/vehicle-details")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicleDetails)));
        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.brandName", CoreMatchers.is(vehicleDetails.getBrandName())))
                .andExpect(jsonPath("$.modelName", CoreMatchers.is(vehicleDetails.getModelName())));
    }

    @Test
    public void fetchAllVehicleDetails_ReturnOK() throws VehicleDetailsNotFound, Exception {
        VehicleDetails vehicleDetails1 = new VehicleDetails(1, 2020, "Volvo", "Arteon", "SE", "Sedan", 64496.759, 1500, 2.16, "Perth, Australia", "The Volkswagen Arteon is a car manufactured by German car manufacturer Volkswagen.\n" +
                "Described as a large family car or a mid-size car, it is available in five-door \n" +
                "liftback or estate body styles.", "Nexus Motors Ltd", "+880185365127");
        VehicleDetails vehicleDetails2 = new VehicleDetails(2, 2020, "Honda", "Arteon", "SE", "Sedan", 64496.759, 1500, 2.16, "Perth, Australia", "The Volkswagen Arteon is a car manufactured by German car manufacturer Volkswagen.\n" +
                "Described as a large family car or a mid-size car, it is available in five-door \n" +
                "liftback or estate body styles.", "Nexus Motors Ltd", "+880185365127");
        List<VehicleDetails> output = Arrays.asList(vehicleDetails1, vehicleDetails2);
        when(vehicleDetailsService.fetchAllVehicleDetails()).thenReturn(output);
        ResultActions response = mockMvc.perform(get("/api/v1/vehicle-details")
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.vehicleDetailsList.size()", Matchers.is(2)))
                .andExpect(jsonPath("$.vehicleDetailsList[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$.vehicleDetailsList[0].brandName", Matchers.is("Volvo")))
                .andExpect(jsonPath("$.vehicleDetailsList[1].brandName", Matchers.is("Honda")))
                .andExpect(jsonPath("$.vehicleDetailsList[1].id", Matchers.is(2)));
    }

    @Test
    public void Get_VehicleDetails_ByID() throws VehicleDetailsNotFound, Exception {
        int vehicleId = 1;
        when(vehicleDetailsService.getVehicleById(vehicleId)).thenReturn(vehicleDetails);
        mockMvc.perform(get("/api/v1/vehicle-details/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicleDetails)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandName", CoreMatchers.is(vehicleDetails.getBrandName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modelName", CoreMatchers.is(vehicleDetails.getModelName())));
    }
}