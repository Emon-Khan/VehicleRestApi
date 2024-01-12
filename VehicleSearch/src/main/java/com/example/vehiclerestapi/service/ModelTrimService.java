package com.example.vehiclerestapi.service;

import com.example.vehiclerestapi.entity.Manufacturer;
import com.example.vehiclerestapi.entity.Model;
import com.example.vehiclerestapi.entity.TrimType;
import com.example.vehiclerestapi.exception.ManufacturerNotFoundException;
import com.example.vehiclerestapi.exception.ModelNotFoundException;
import com.example.vehiclerestapi.exception.TrimTypeNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ModelTrimService {
    Model saveModel(Model model);

    TrimType saveTrimType(TrimType trimType);

    List<Model> getAllModels();
    Optional<Model> getModelById(int id) throws ModelNotFoundException;
    TrimType getTrimTypeById(int id) throws TrimTypeNotFoundException;
    public Manufacturer getManufacturerById(int id) throws ManufacturerNotFoundException;

    Model modifyModel(int id, Model model) throws ModelNotFoundException;

    TrimType modifyTrimType(int id, TrimType trimType) throws TrimTypeNotFoundException;
    void deleteModelById(int id) throws ModelNotFoundException;
    List<Model> getModelsByManufacturerId(int manufacturerId) throws ManufacturerNotFoundException;
    Model updateModelTrim(int modelId, Model model) throws ModelNotFoundException, TrimTypeNotFoundException, ManufacturerNotFoundException;
}
