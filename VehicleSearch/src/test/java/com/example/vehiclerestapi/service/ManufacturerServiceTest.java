package com.example.vehiclerestapi.service;

import com.example.vehiclerestapi.dao.ManufacturerDAO;
import com.example.vehiclerestapi.entity.Manufacturer;
import com.example.vehiclerestapi.exception.ManufacturerNotFoundException;
import com.example.vehiclerestapi.service.impl.ManufacturerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
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
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class ManufacturerServiceTest {
    Manufacturer manufacturer;
    @InjectMocks
    ManufacturerServiceImpl manufacturerService;
    @Mock
    private ManufacturerDAO manufacturerDAO;

    @BeforeEach
    public void init() {
        manufacturer = Manufacturer.builder()
                .manufacturerName("Volswagen CC")
                .countryOfOrigin("China").build();
    }

    @Test
    public void ManufacturerService_saveManufacturer_ReturnManufacturerIsNotNull() {
        //Arrange
        //It has been defined within the init
        Mockito.when(manufacturerDAO.save(Mockito.any(Manufacturer.class))).thenReturn(manufacturer);

        //Act
        Manufacturer savedManufacturer = manufacturerService.saveManufacturer(manufacturer);

        //Assert
        assertThat(savedManufacturer).isNotNull();
    }

    @Test
    public void ManufacturerService_fetchAllManufacturers_ReturnManufacturerIsNotNullandSizeOfManufacturer() {
        //Arrange
        //It has been defined within the init
        Manufacturer manufacturer1 = Manufacturer.builder()
                .manufacturerName("Honda")
                .countryOfOrigin("Japan").build();
        List<Manufacturer> listOfManufactuer = Arrays.asList(manufacturer, manufacturer1);
        Mockito.when(manufacturerDAO.findAll()).thenReturn(listOfManufactuer);

        //Act
        List<Manufacturer> allManufacturers = manufacturerService.fetchAllManufacturers();

        //Assert
        assertThat(allManufacturers).isNotNull();
        assertThat(allManufacturers.size()).isEqualTo(2);
    }

    @Test
    public void ManufacturerService_getManufacturerById_ReturnManufacturerIsNotNull() {
        //Arrange
        //It has been defined within the init
        Mockito.when(manufacturerDAO.findById(manufacturer.getId())).thenReturn(Optional.ofNullable(manufacturer));

        //Act
        Manufacturer findManufacturer = manufacturerService.getManufacturerById(manufacturer.getId());

        //Assert
        assertThat(findManufacturer).isNotNull();
    }

    @Test
    public void ManufacturerService_updateManufacturer_ReturnManufacturerIsNotNull() {
        //Arrange
        //It has been defined within the init
        Manufacturer manufacturer1 = Manufacturer.builder()
                .manufacturerName("Honda")
                .countryOfOrigin("Japan").build();
        Mockito.when(manufacturerDAO.findById(manufacturer.getId())).thenReturn(Optional.ofNullable(manufacturer));
        Mockito.when(manufacturerDAO.save(Mockito.any(Manufacturer.class))).thenReturn(manufacturer);

        //Act
        Manufacturer updatedManufacturer = manufacturerService.updateManufacturer(manufacturer.getId(), manufacturer1);

        //Assert
        assertThat(updatedManufacturer).isNotNull();
    }

    @Test
    public void ManufacturerService_deleteManufacturerByID_ReturnManufacturerIsNull() throws ManufacturerNotFoundException {
        //Arrange
        //It has been defined within the init
        Mockito.when(manufacturerDAO.findById(manufacturer.getId())).thenReturn(Optional.ofNullable(manufacturer));

        //Act
        //Assert
        assertAll(()->manufacturerService.deleteManufacturerByID(manufacturer.getId()));
    }
}
