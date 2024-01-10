package com.example.vehiclerestapi.dao;

import com.example.vehiclerestapi.entity.VehicleDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class VehicleDetailsDAOTest {
    VehicleDetails vehicleDetails;
    @Autowired
    private VehicleDetailsDAO vehicleDetailsDAO;

    @Test
    void getVehicleDetailsByIDTest() {
        //Arrange
        VehicleDetails dbVehicleDetails = new VehicleDetails(105, 2020, "Volkswagen", "Arteon", "SE", "Sedan", 64496.759, 1500, 2.16, "Perth, Australia", "The Volkswagen Arteon is a car manufactured by German car manufacturer Volkswagen.Described as a large family car or a mid-size car, it is available in five-door liftback or estate body styles.", "Nexus Motor Ltd", "+880185365127");
        vehicleDetailsDAO.save(dbVehicleDetails);
        //Act
        VehicleDetails findVehicleDetailsUsingID = vehicleDetailsDAO.findById(dbVehicleDetails.getId()).get();
        //VehicleDetails findVehicleDetailsUsingID = optionalVehicleDetailsUsingID.get();
        //Assert
        assertThat(findVehicleDetailsUsingID).isNotNull();
    }

    @Nested
    public class useBeforeEach {
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
        public void VehicleDetails_Save_ReturnedSavedVehicleDetails() {
            //Arrange
            //This part has been writen on the @BeforeEach section so that I can use it whenever I want
            //Act
            VehicleDetails savedVehicleDetails = vehicleDetailsDAO.save(vehicleDetails);

            //Assert
            assertThat(savedVehicleDetails).isNotNull();
            assertThat(savedVehicleDetails.getId()).isGreaterThan(0);
        }

        @Test
        public void getAllVehicleDetails() {
            //Arrange
            VehicleDetails vehicleDetails2 = VehicleDetails.builder()
                    .modelYear(2022)
                    .brandName("Toyota")
                    .modelName("Camry")
                    .trimType("LS")
                    .bodyType("")
                    .vehiclePrice(25468.65)
                    .milesOnVehicle(1500)
                    .interestRate(5.35)
                    .locationOfVehicle("Albuquerque, New Mexico")
                    .vehicleDescription("There is no defect in this car.Before purchasing you can check this car for a long period of time.")
                    .sellerName("DS Auto")
                    .sellerContactNumber("+8801967899852").build();


            vehicleDetailsDAO.save(vehicleDetails);
            vehicleDetailsDAO.save(vehicleDetails2);
            //Act
            List<VehicleDetails> getVehicleDetails = vehicleDetailsDAO.findAll();
            //Assert
            assertThat(getVehicleDetails).isNotNull();
            assertThat(getVehicleDetails.size()).isEqualTo(2);
        }
    }
}