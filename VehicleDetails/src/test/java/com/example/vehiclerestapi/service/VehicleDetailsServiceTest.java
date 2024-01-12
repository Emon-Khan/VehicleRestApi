package com.example.vehiclerestapi.service;

import com.example.vehiclerestapi.dao.VehicleDetailsDAO;
import com.example.vehiclerestapi.entity.VehicleDetails;
import com.example.vehiclerestapi.exception.VehicleDetailsNotFound;
import com.example.vehiclerestapi.exception.VehicleNotSaved;
import com.example.vehiclerestapi.service.impl.VehicleDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(MockitoExtension.class)
class VehicleDetailsServiceTest {

    VehicleDetails vehicleDetails;
    @Mock
    private VehicleDetailsDAO vehicleDetailsDAO;
    @InjectMocks
    private VehicleDetailsServiceImpl vehicleDetailsService;

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
    @DisplayName("Test vehicle details saved when passed valid input data")
    void VehicleDetailsService_saveVehicleDetails_ReturnIsNotNull() throws VehicleNotSaved {
        //Arrange
        //It has been defined within the init
        Mockito.when(vehicleDetailsDAO.save(Mockito.any(VehicleDetails.class))).thenReturn(vehicleDetails);

        //Act
        VehicleDetails output = vehicleDetailsService.saveVehicleDetails(vehicleDetails);

        //Assert
        assertThat(output).isNotNull();
    }

    @Test
    void VehicleDetailsService_fetchAllVehicleDetails_ReturnIsNotNullandCheckSize() throws VehicleDetailsNotFound {
        //Arrange
        //It has been defined within the init
        VehicleDetails vehicleDetails2 = VehicleDetails.builder()
                .modelYear(2022)
                .brandName("Toyota")
                .modelName("Camry")
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
        VehicleDetails vehicleDetails3 = VehicleDetails.builder()
                .modelYear(2021)
                .brandName("Toyota")
                .modelName("Corolla")
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

        List<VehicleDetails> input = Arrays.asList(vehicleDetails, vehicleDetails2, vehicleDetails3);
        Mockito.when(vehicleDetailsDAO.findAll()).thenReturn(input);

        //Act
        List<VehicleDetails> output = vehicleDetailsService.fetchAllVehicleDetails();

        //Assert
        assertThat(output).isNotNull();
        assertThat(output.size()).isEqualTo(3);
    }

    @Test
    void VehicleDetailsService_getVehicleByID_ReturnIsNotNull() throws VehicleDetailsNotFound {
        //Arrange
        //It has been defined within the init
        Mockito.when(vehicleDetailsDAO.findById(vehicleDetails.getId())).thenReturn(Optional.ofNullable(vehicleDetails));

        //Act
        VehicleDetails savedVehicleDetails = vehicleDetailsService.getVehicleById(vehicleDetails.getId());

        //Assert
        assertThat(savedVehicleDetails).isNotNull();
    }
}