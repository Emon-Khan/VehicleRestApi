package com.example.vehiclerestapi.service.impl;

import com.example.vehiclerestapi.dao.ManufacturerDAO;
import com.example.vehiclerestapi.dao.ModelDAO;
import com.example.vehiclerestapi.dao.TrimTypeDAO;
import com.example.vehiclerestapi.entity.Manufacturer;
import com.example.vehiclerestapi.entity.Model;
import com.example.vehiclerestapi.entity.TrimType;
import com.example.vehiclerestapi.exception.ManufacturerNotFoundException;
import com.example.vehiclerestapi.exception.ModelNotFoundException;
import com.example.vehiclerestapi.exception.TrimTypeNotFoundException;
import com.example.vehiclerestapi.service.ModelTrimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class ModelTrimServiceImpl implements ModelTrimService {
    @Autowired
    private ModelDAO modelDAO;
    @Autowired
    private TrimTypeDAO trimTypeDAO;
    @Autowired
    private ManufacturerDAO manufacturerDAO;

    @Override
    public Model saveModel(Model model) {
        Model saveModel = modelDAO.save(model);
        return saveModel;
    }

    @Override
    public TrimType saveTrimType(TrimType trimType) {
        return trimTypeDAO.save(trimType);
    }

    @Override
    public List<Model> getAllModels() {
        List<Model> saveModels = modelDAO.findAll();
        return saveModels;
    }

    @Override
    public Model getModelById(int id) throws ModelNotFoundException {
        Model checkModelId = modelDAO.findById(id).orElseThrow(
                () -> new ModelNotFoundException("Model has not been found for this ID " + id));
        return checkModelId;
    }

    @Override
    public TrimType getTrimTypeById(int id) throws TrimTypeNotFoundException {
        TrimType checkTrimTypeId = trimTypeDAO.findById(id).orElseThrow(
                () -> new TrimTypeNotFoundException("Trim type has not been found for this ID " + id));
        return checkTrimTypeId;
    }

    @Override
    public Model modifyModel(int id, Model model) throws ModelNotFoundException {
        Model detailsOfModel = modelDAO.findById(id).orElseThrow(
                () -> new ModelNotFoundException("Model has not been found for this ID " + id));
        if (Objects.nonNull(model)) {
            if (Objects.nonNull(model.getModelName()) && !"".equalsIgnoreCase(model.getModelName())) {
                detailsOfModel.setModelName(model.getModelName());
            }
            detailsOfModel = modelDAO.save(detailsOfModel);
        }
        return detailsOfModel;
    }

    @Override
    public TrimType modifyTrimType(int id, TrimType trimType) throws TrimTypeNotFoundException {
        TrimType detailsOfTrimType = trimTypeDAO.findById(id).orElseThrow(
                () -> new TrimTypeNotFoundException("TrimType has not been found for this ID " + id));
        if (Objects.nonNull(trimType)) {
            if (Objects.nonNull(trimType.getTrimType()) && !"".equalsIgnoreCase(trimType.getTrimType())) {
                detailsOfTrimType.setTrimType(trimType.getTrimType());
            }
            detailsOfTrimType = trimTypeDAO.save(detailsOfTrimType);
        }
        return detailsOfTrimType;
    }

    @Override
    public void deleteModelById(int id) throws ModelNotFoundException {
        Model modelID = getModelById(id);
        modelDAO.deleteById(id);
    }

    @Override
    public List<Model> getModelsByManufacturerId(int manufacturerId) throws ManufacturerNotFoundException {
        Optional<Manufacturer> manufacturerFromDB = manufacturerDAO.findById(manufacturerId);
        if (manufacturerFromDB.isEmpty()) {
            throw new ManufacturerNotFoundException("Manufacturer has not been found for this ID " + manufacturerId);
        }
        Manufacturer detailsOfManufacturer = manufacturerFromDB.get();
        List<Model> modelList = modelDAO.findByManufacturer(detailsOfManufacturer);
        return modelList;
    }

    @Override
    public Model updateModelTrim(int modelId, Model model)
            throws ModelNotFoundException, TrimTypeNotFoundException, ManufacturerNotFoundException {
        Model modelDetails = getModelById(modelId);
        List<TrimType> trimTypeDetailsList = model.getTrimTypeList();
        Manufacturer manufacturerDetails = model.getManufacturer();
        if ((!"".equalsIgnoreCase(model.getModelName())) &&
                !Objects.equals(modelDetails.getModelName(), model.getModelName())) {
            modelDetails.setModelName(model.getModelName());
        }
        for (TrimType trimType : trimTypeDetailsList) {
            TrimType trimTypeDetails = getTrimTypeById(trimType.getId());
            if (!(modelDetails.getTrimTypeList().contains(trimType))) {
                modelDetails.getTrimTypeList().add(trimTypeDetails);
            }
        }
        for (int i = trimTypeDetailsList.size(); i < modelDetails.getTrimTypeList().size(); i++) {
            if (trimTypeDetailsList.size() != modelDetails.getTrimTypeList().size()) {
                modelDetails.getTrimTypeList().remove(i);
            }
        }
        Manufacturer detailsOfManufacturer = manufacturerDAO.findById(manufacturerDetails.getId()).orElseThrow(
                () -> new ManufacturerNotFoundException("Manufacturer has not been found for this ID " + manufacturerDetails.getId()));
        if (!manufacturerDetails.equals(modelDetails.getManufacturer())) {
            modelDetails.setManufacturer(manufacturerDetails);
        }
        modelDetails = modelDAO.save(modelDetails);
        return modelDetails;
    }

}
