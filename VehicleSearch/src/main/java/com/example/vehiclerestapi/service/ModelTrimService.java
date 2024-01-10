package com.example.vehiclerestapi.service;

import com.example.vehiclerestapi.entity.Model;
import com.example.vehiclerestapi.entity.TrimType;
import com.example.vehiclerestapi.exception.ManufacturerNotFoundException;
import com.example.vehiclerestapi.exception.ModelNotFoundException;
import com.example.vehiclerestapi.exception.TrimTypeNotFoundException;

import java.util.List;

public interface ModelTrimService {
    Model saveModel(Model model);

    TrimType saveTrimType(TrimType trimType);

    List<Model> getAllModels();
    Model getModelById(int id) throws ModelNotFoundException;
    TrimType getTrimTypeById(int id) throws TrimTypeNotFoundException;

    Model modifyModel(int id, Model model) throws ModelNotFoundException;

    TrimType modifyTrimType(int id, TrimType trimType) throws TrimTypeNotFoundException;
    void deleteModelById(int id) throws ModelNotFoundException;
    List<Model> getModelsByManufacturerId(int manufacturerId) throws ManufacturerNotFoundException;
    Model updateModelTrim(int modelId, Model model) throws ModelNotFoundException, TrimTypeNotFoundException, ManufacturerNotFoundException;
}
