package com.example.vehiclerestapi.dao;

import com.example.vehiclerestapi.BaseTest;
import com.example.vehiclerestapi.VehicleSearchApplication;
import com.example.vehiclerestapi.entity.TrimType;
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
public class TrimTypeDAOTest extends BaseTest {
    TrimType trimType;
    @Autowired
    TrimTypeDAO trimTypeDAO;

    @BeforeEach
    public void init() {
        trimType = TrimType.builder()
                .trimType("Ex").build();
    }


    @Test
    public void TrimTypeDAO_Save_ReturnedSavedTrimType() {
        //Arrange
        //It has been defined within the init

        //Act
        TrimType SavedTrimType = trimTypeDAO.save(trimType);

        //Assert
        assertThat(SavedTrimType).usingRecursiveComparison().ignoringFields("id").isEqualTo(trimType);
    }

    @Test
    public void TrimTypeDAO_FindByID_ReturnIsNotNull() {
        //Arrange
        //It has been defined within the init
        trimTypeDAO.save(trimType);

        //Act
        TrimType findTrimTypeUsingID = trimTypeDAO.findById(trimType.getId()).get();

        //Assert
        assertThat(findTrimTypeUsingID).isNotNull();
    }

}
