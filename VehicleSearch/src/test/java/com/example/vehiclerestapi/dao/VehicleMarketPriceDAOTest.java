package com.example.vehiclerestapi.dao;

import com.example.vehiclerestapi.BaseTest;
import com.example.vehiclerestapi.VehicleSearchApplication;
import com.example.vehiclerestapi.entity.VehicleMarketPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = VehicleSearchApplication.class)
public class VehicleMarketPriceDAOTest extends BaseTest {
    VehicleMarketPrice vehicleMarketPrice;
    @Autowired
    VehicleMarketPriceDAO vehicleMarketPriceDAO;

    @BeforeEach
    public void init() {
        vehicleMarketPrice = VehicleMarketPrice.builder()
                .modelYear(2019)
                .brandName("Volkswagen")
                .modelName("Arteon")
                .trimType("SEL")
                .price(37000).build();
    }

    @Test
    public void VehicleMarketPriceDAO_FindByBrandNameAndModelNameAndTrimTypeAndModelYear_ReturnedIsNotNull() {
        //Arrange
        //Arrange has defined under init
        vehicleMarketPriceDAO.save(vehicleMarketPrice);

        //Act
        VehicleMarketPrice vehicleMarketPriceReturn = vehicleMarketPriceDAO.findByBrandNameAndModelNameAndTrimTypeAndModelYear(vehicleMarketPrice.getBrandName(), vehicleMarketPrice.getModelName(), vehicleMarketPrice.getTrimType(), vehicleMarketPrice.getModelYear()).get();

        //Assert
        assertThat(vehicleMarketPriceReturn).isNotNull();

    }

    @Test
    public void VehicleMarketPriceDAO_Save_ReturnedSavedVehicleMarketPrice() {
        //Arrange
        //Arrange has defined under init

        //Act
        VehicleMarketPrice savedVehicleMarketPrice = vehicleMarketPriceDAO.save(vehicleMarketPrice);

        //Assert
        assertThat(savedVehicleMarketPrice).usingRecursiveComparison().ignoringFields("id").isEqualTo(vehicleMarketPrice);
    }

}

