package com.example.vehiclerestapi.dao;

import com.example.vehiclerestapi.BaseTest;
import com.example.vehiclerestapi.VehicleSearchApplication;
import com.example.vehiclerestapi.entity.Manufacturer;
import com.example.vehiclerestapi.entity.Model;
import com.example.vehiclerestapi.entity.TrimType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = VehicleSearchApplication.class)
public class ModelDAOTest extends BaseTest {
    Model model;
    Manufacturer manufacturer;
    @Autowired
    ModelDAO modelDAO;

    @BeforeEach
    public void init() {
        List<TrimType> listOfTrimType = List.of(
                new TrimType(1, "SEL"),
                new TrimType(2, "SEL R-Line"));
        manufacturer = new Manufacturer(1, "Volswagen CC", "China");
        model = new Model(1, "Arteon", listOfTrimType, manufacturer);
    }

    @Test
    public void ModelDAO_Save_ReturnSavedModel() {
        //Arrange
        //It has been defined within the init

        //Act
        Model savedModel = modelDAO.save(model);

        //Assert
        assertThat(savedModel).usingRecursiveComparison().ignoringFields("id").isEqualTo(model);
    }

    @Test
    public void ModelDAO_FindAll_ReturnSizeOfModel() {
        //Arrange
        List<TrimType> listOfTrimType = List.of(
                new TrimType(1, "SEL"),
                new TrimType(2, "SEL R-Line"),
                new TrimType(3, "SE"),
                new TrimType(4, "GTI"));
        //Manufacturer manufacturer = new Manufacturer(1, "Volswagen CC", "China");
        Model model1 = new Model(2, "Polo", listOfTrimType, manufacturer);
        //modelDAO.save(model);
        //modelDAO.save(model1);
        //Act
        List<Model> listOfModel = modelDAO.findAll();

        //Assert
        assertThat(listOfModel.size()).isEqualTo(2);
    }

    @Test
    public void ModelDAO_FindByID_ReturnIsNotNull() {
        //Arrange
        //It has been defined within the init
        modelDAO.save(model);

        //Act
        Model findModelUsingID = modelDAO.findById(model.getId()).get();

        //Assert
        assertThat(findModelUsingID).isNotNull();
    }
    @Test
    public void ModelDAO_FindByManufacturer_ReturnIsNotNullAndSizeEquals(){
        //Arrange
        //It has been defined within the init
        List<TrimType> listOfTrimType = List.of(
                new TrimType(1, "SEL"),
                new TrimType(2, "SEL R-Line"),
                new TrimType(3, "SE"),
                new TrimType(4, "GTI"));
        //Manufacturer manufacturer = new Manufacturer(1, "Volswagen CC", "China");
        Model model1 = new Model(2, "Polo", listOfTrimType, manufacturer);
        //modelDAO.save(model);
        //modelDAO.save(model1);

        //Act
        List<Model> ListOfModelUsingManufacturer = modelDAO.findByManufacturer(manufacturer);

        //Assert
        assertThat(ListOfModelUsingManufacturer).isNotNull();
        assertThat(ListOfModelUsingManufacturer.size()).isEqualTo(2);
    }
}
