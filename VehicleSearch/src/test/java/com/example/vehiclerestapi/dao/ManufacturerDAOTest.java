package com.example.vehiclerestapi.dao;

import com.example.vehiclerestapi.BaseTest;
import com.example.vehiclerestapi.VehicleSearchApplication;
import com.example.vehiclerestapi.entity.Manufacturer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = VehicleSearchApplication.class)
public class ManufacturerDAOTest extends BaseTest {
    Manufacturer manufacturer;
    @Autowired
    ManufacturerDAO manufacturerDAO;

    @BeforeEach
    void init() {
        manufacturer = Manufacturer.builder()
                .manufacturerName("Volswagen CC")
                .countryOfOrigin("China").build();
    }

    @Test
    public void ManufacturerDAO_Save_ReturnedSavedManufacturer() {
        //Arrange
        //It has been defined within the init

        //Act
        Manufacturer SavedManufacturer = manufacturerDAO.save(manufacturer);

        //Assert
        assertThat(SavedManufacturer).usingRecursiveComparison().ignoringFields("id").isEqualTo(manufacturer);
    }

    @Test
    public void ManufacturerDAO_FindById_ReturnIsNotNull() {
        //Arrange
        //It has been defined within the init
        manufacturerDAO.save(manufacturer);

        //Act
        Manufacturer findManufacturerUsingID = manufacturerDAO.findById(manufacturer.getId()).get();

        //Assert
        assertThat(findManufacturerUsingID).isNotNull();
    }

    @Test
    public void ManufacturerDAO_FindAll_ReturnSizeOfManufacturer() {
        //Arrange
        Manufacturer manufacturer1 = Manufacturer.builder()
                .manufacturerName("Honda")
                .countryOfOrigin("Japan").build();
        manufacturerDAO.save(manufacturer1);

        //Act
        List<Manufacturer> listOfManufacturer = manufacturerDAO.findAll();

        //Assert
        assertThat(listOfManufacturer.size()).isEqualTo(2);
    }

    @Test
    public void ManufacturerDAO_DeleteById_ReturnIsEmpty() {
        //Arrange
        //It has been defined within the init
        manufacturerDAO.save(manufacturer);

        //Act
        manufacturerDAO.deleteById(manufacturer.getId());
        Optional<Manufacturer> FindingManufacturer = manufacturerDAO.findById(manufacturer.getId());

        //Assert
        assertThat(FindingManufacturer).isEmpty();
    }
}
