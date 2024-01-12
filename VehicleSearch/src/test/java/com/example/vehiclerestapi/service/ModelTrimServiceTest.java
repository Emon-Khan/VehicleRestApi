package com.example.vehiclerestapi.service;

import com.example.vehiclerestapi.dao.ManufacturerDAO;
import com.example.vehiclerestapi.dao.ModelDAO;
import com.example.vehiclerestapi.dao.TrimTypeDAO;
import com.example.vehiclerestapi.entity.Manufacturer;
import com.example.vehiclerestapi.entity.Model;
import com.example.vehiclerestapi.entity.TrimType;
import com.example.vehiclerestapi.exception.ManufacturerNotFoundException;
import com.example.vehiclerestapi.exception.ModelNotFoundException;
import com.example.vehiclerestapi.exception.TrimTypeNotFoundException;
import com.example.vehiclerestapi.service.impl.ModelTrimServiceImpl;
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
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ModelTrimServiceTest {
    Model model;
    TrimType trimType;
    @Mock
    private TrimTypeDAO trimTypeDAO;
    @Mock
    private ModelDAO modelDAO;
    @Mock
    private ManufacturerDAO manufacturerDAO;

    @InjectMocks
    private ModelTrimServiceImpl modelTrimService;

    @BeforeEach
    public void init() {
        List<TrimType> listOfTrimType = List.of(
                new TrimType(2, "SEL"),
                new TrimType(3, "SEL-R-Line")
        );
        model = new Model(
                1, "Arteon", listOfTrimType, new Manufacturer(
                1, "Volswagen CC", "China"));
    }

    @Test
    public void ModelTrimService_saveModel_ReturnModelIsNotNull() {
        //Arrange
        //It has been defined within the init
        Mockito.when(modelDAO.save(any(Model.class))).thenReturn(model);

        //Act
        Model savedModel = modelTrimService.saveModel(model);

        //Assert
        assertThat(savedModel).isNotNull();
    }

    @Test
    public void ModelTrimService_saveTrimType_ReturnTrimTypeIsNotNull() {
        //Arrange
        trimType = new TrimType(1, "SEL");
        Mockito.when(trimTypeDAO.save(any(TrimType.class))).thenReturn(trimType);

        //Act
        TrimType savedTrimType = modelTrimService.saveTrimType(trimType);

        //Assert
        assertThat(savedTrimType).isNotNull();
    }

    @Test
    public void ModelTrimService_getAllModels_ReturnModelIsNotNullandSizeOfModel() {
        //Arrange
        List<TrimType> listOfTrimType = List.of(
                new TrimType(1, "SE"),
                new TrimType(2, "SEL"),
                new TrimType(3, "SEL-R-Line"),
                new TrimType(4, "GTI")
        );
        Model model1 = new Model(
                2, "Polo", listOfTrimType, new Manufacturer(
                1, "Volswagen CC", "China"));
        List<Model> listOfModel = Arrays.asList(model, model1);
        Mockito.when(modelDAO.findAll()).thenReturn(listOfModel);

        //Act
        List<Model> allModels = modelTrimService.getAllModels();

        //Assert
        assertThat(allModels).isNotNull();
        assertThat(allModels.size()).isEqualTo(2);
    }

    @Test
    public void ModelTrimService_getModelById_ReturnModelIsNotNull() throws ModelNotFoundException {
        //Arrange
        //It has been defined within the init
        Mockito.when(modelDAO.findById(model.getId())).thenReturn(Optional.ofNullable(model));

        //Act
        Model findModel = modelTrimService.getModelById(model.getId());

        //Assert
        assertThat(findModel).isNotNull();
    }

    @Test
    public void ModelTrimService_getModelById_WillThrowModelNotFound() {
        //Given
        int id = 10;
        given(modelDAO.findById(id))
                .willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(()->
                modelTrimService.getModelById(id))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("Model has not been found for this ID " + id);
    }

    @Test
    public void ModelTrimService_getTrimById_ReturnModelIsNotNull() throws TrimTypeNotFoundException {
        //Arrange
        trimType = new TrimType(1, "SEL");
        Mockito.when(trimTypeDAO.findById(trimType.getId())).thenReturn(Optional.ofNullable(trimType));

        //Act
        TrimType findTrimType = modelTrimService.getTrimTypeById(trimType.getId());

        //Assert
        assertThat(findTrimType).isNotNull();
    }
    @Test
    public void ModelTrimService_getTrimById_WillThrowTrimTypeNotFound() {
        //Given
        int id = 10;
        given(trimTypeDAO.findById(id))
                .willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(()->
                modelTrimService.getTrimTypeById(id))
                .isInstanceOf(TrimTypeNotFoundException.class)
                .hasMessageContaining("Trim type has not been found for this ID " + id);
    }

    @Test
    @DisplayName("This service will only change the value of modelName")
    public void ModelTrimService_modifyModel_ReturnModelIsNotNull() throws ModelNotFoundException {
        //Arrange
        //It has been defined within the init
        List<TrimType> listOfTrimType = List.of(
                new TrimType(1, "SE"),
                new TrimType(2, "SEL"),
                new TrimType(3, "SEL-R-Line"),
                new TrimType(4, "GTI")
        );
        Model model1 = new Model(
                2, "Polo", listOfTrimType, new Manufacturer(
                1, "Volswagen CC", "China"));
        Mockito.when(modelDAO.findById(model.getId())).thenReturn(Optional.ofNullable(model));
        Mockito.when(modelDAO.save(any(Model.class))).thenReturn(model);

        //Act
        Model modifyModel = modelTrimService.modifyModel(model.getId(), model1);

        //Assert
        assertThat(modifyModel).isNotNull();
    }
    @Test
    public void ModelTrimService_modifyModel_WillThrowModelNotFound() {
        //Given
        int id = 10;
        given(modelDAO.findById(id))
                .willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(()->
                modelTrimService.modifyModel(id,any()))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("Model has not been found for this ID " + id);
    }

    @Test
    @DisplayName("This service will only change the value of modelName")
    public void ModelTrimService_modifyTrimType_ReturnTrimTypeIsNotNull() throws TrimTypeNotFoundException {
        //Arrange
        trimType = new TrimType(1, "SEL");
        TrimType trimType1 = new TrimType(2, "SEL-R-Line");
        Mockito.when(trimTypeDAO.findById(trimType.getId())).thenReturn(Optional.ofNullable(trimType));
        Mockito.when(trimTypeDAO.save(any(TrimType.class))).thenReturn(trimType);

        //Act
        TrimType modifyTrimType = modelTrimService.modifyTrimType(trimType.getId(), trimType1);

        //Assert
        assertThat(modifyTrimType).isNotNull();
    }
    @Test
    public void ModelTrimService_modifyTrimType_WillThrowTrimTypeNotFound() {
        //Given
        int id = 10;
        given(trimTypeDAO.findById(id))
                .willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(()->
                modelTrimService.modifyTrimType(id,any()))
                .isInstanceOf(TrimTypeNotFoundException.class)
                .hasMessageContaining("TrimType has not been found for this ID " + id);
    }

    @Test
    public void ModelTrimService_deleteModelByID_ReturnModelIsNull() {
        //Arrange
        //It has been defined within the init
        Mockito.when(modelDAO.findById(model.getId())).thenReturn(Optional.ofNullable(model));

        //Act
        //Assert
        assertAll(() -> modelTrimService.deleteModelById(model.getId()));
    }
    /*@Test
    public void ModelTrimService_deleteModelByID_WillThrowModelNotFound() throws ModelNotFoundException {
        //Given
        int id = 10;
        given(modelTrimService.getModelById(id))
                .willReturn(Optional.ofNullable());

        //when
        //then
        assertThatThrownBy(()->
                modelTrimService.deleteModelById(id))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("Model has not been found for this ID " + id);
    }*/

    @Test
    public void ModelTrimService_getModelsByManufacturerId_ReturnModelIsNotNullandSizeOfModel() throws ManufacturerNotFoundException {
        //Arrange
        List<TrimType> listOfTrimType = List.of(
                new TrimType(1, "SE"),
                new TrimType(2, "SEL"),
                new TrimType(3, "SEL-R-Line"),
                new TrimType(4, "GTI")
        );
        Manufacturer manufacturer = new Manufacturer(1, "Volswagen CC", "China");
        Model model1 = new Model(
                2, "Polo", listOfTrimType, manufacturer);
        List<Model> listOfModel = Arrays.asList(model, model1);
        Mockito.when(manufacturerDAO.findById(manufacturer.getId())).thenReturn(Optional.of(manufacturer));
        Mockito.when(modelDAO.findByManufacturer(manufacturer)).thenReturn(listOfModel);

        //Act
        List<Model> modelFindByManufacturerID = modelTrimService.getModelsByManufacturerId(manufacturer.getId());

        //Assert
        assertThat(modelFindByManufacturerID).isNotNull();
        assertThat(modelFindByManufacturerID.size()).isEqualTo(2);
    }
    @Test
    public void ModelTrimService_getModelsByManufacturerId_WillThrowManufacturerNotFound() {
        //Given
        int id = 10;
        given(manufacturerDAO.findById(id))
                .willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(()->
                modelTrimService.getModelsByManufacturerId(id))
                .isInstanceOf(ManufacturerNotFoundException.class)
                .hasMessageContaining("Manufacturer has not been found for this ID " + id);
    }

    /*@Test
    public void ModelTrimService_updateModelTrim_ReturnModelTrimIsNotNull() throws TrimTypeNotFoundException, ModelNotFoundException, ManufacturerNotFoundException {
        //Arrange
        //It has been defined within the init
        int passedModelID = 1;
        List<TrimType> listOfTrimType = List.of(
                new TrimType(1, "SE"),
                new TrimType(2, "SEL"),
                new TrimType(3, "SEL-R-Line"),
                new TrimType(4, "GTI")
        );
        manufacturer = new Manufacturer(1, "Volswagen CC", "China");
        Model passedModel = new Model(2, "Polo", listOfTrimType, manufacturer);

        Mockito.when(modelDAO.findById(passedModelID)).thenReturn(Optional.of(model));

        passedModel.setTrimTypeList(listOfTrimType);
        passedModel.setManufacturer(manufacturer);
        passedModel.setModelName(passedModel.getModelName());
        model.setModelName(model.getModelName());

        for (TrimType temp : listOfTrimType) {
            Mockito.when(trimTypeDAO.findById(temp.getId())).thenReturn(Optional.ofNullable(temp));
        }
        Mockito.when(manufacturerDAO.findById(passedModel.getManufacturer().getId())).thenReturn(Optional.ofNullable(manufacturer));
        Mockito.when(modelDAO.save(Mockito.any(Model.class))).thenReturn(model);

        //Act
        Model updatedModelTrim = modelTrimService.updateModelTrim(model.getId(), passedModel);
        //Model modelDetailsClass = modelDAO.findById(passedModelID).get();
        //TrimType trimTypeDetailsList = passedModel.setTrimTypeList(passedModel);
        //Manufacturer manufacturerDetails = manufacturerService.getManufacturerById(manufacturer.getId());

        //Assert
        assertThat(updatedModelTrim).isNotNull();
        //assertThat(modelDetails).isNotNull();

        //manufacturerDAO.findById(manufacturerDetails.getId()).get()
    }*/

}
