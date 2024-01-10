package com.example.vehiclerestapi.controller;

import com.example.vehiclerestapi.entity.Model;
import com.example.vehiclerestapi.entity.TrimType;
import com.example.vehiclerestapi.exception.ManufacturerNotFoundException;
import com.example.vehiclerestapi.exception.ModelNotFoundException;
import com.example.vehiclerestapi.exception.TrimTypeNotFoundException;
import com.example.vehiclerestapi.service.ModelTrimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicle-search/model-trim")
public class ModelTrimController {
    @Autowired
    private ModelTrimService modelTrimService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin:create', 'user:create')")
    public ResponseEntity<Model> creatModelTrim(@RequestBody Model model) {
        Model saveRecord = modelTrimService.saveModel(model);
        return new ResponseEntity<>(saveRecord, HttpStatus.CREATED);
    }

    @PutMapping("/{modelId}")
    @PreAuthorize("hasAnyAuthority('admin:update', 'user:update')")
    public ResponseEntity<Model> updateModelTrim(
            @PathVariable int modelId,
            @RequestBody Model model)
            throws ModelNotFoundException, TrimTypeNotFoundException, ManufacturerNotFoundException {
        Model updatedModelTrim = modelTrimService.updateModelTrim(modelId, model);
        return new ResponseEntity<>(updatedModelTrim, HttpStatus.OK);
    }

    @PostMapping("/trim-type")
    @PreAuthorize("hasAnyAuthority('admin:create', 'user:create')")
    public ResponseEntity<TrimType> createTrimType(@RequestBody TrimType trimType) {
        TrimType saveTrimRecord = modelTrimService.saveTrimType(trimType);
        return new ResponseEntity<>(saveTrimRecord, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<List<Model>> fetchAllModels() {
        List<Model> dbModels = modelTrimService.getAllModels();
        if (dbModels.size() > 0) {
            return new ResponseEntity<>(dbModels, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/model/{id}")
    @PreAuthorize("hasAnyAuthority('admin:update', 'user:update')")
    public ResponseEntity<Model> updateModel(@PathVariable int id, @RequestBody Model model) throws ModelNotFoundException {
        Model updatedModel = modelTrimService.modifyModel(id, model);
        return new ResponseEntity<>(updatedModel, HttpStatus.OK);
    }

    @PutMapping("/trim-type/{id}")
    @PreAuthorize("hasAnyAuthority('admin:update', 'user:update')")
    public ResponseEntity<TrimType> updateTrimType(@PathVariable int id, @RequestBody TrimType trimType) throws TrimTypeNotFoundException {
        TrimType updatedTrimType = modelTrimService.modifyTrimType(id, trimType);
        return new ResponseEntity<>(updatedTrimType, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<String> deleteModel(@PathVariable int id) throws ModelNotFoundException {
        modelTrimService.deleteModelById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted model of ID " + id);
    }

    @GetMapping("/manufacturer/{manufacturerId}")
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<List<Model>> findAllModelsForManufacturer(@PathVariable int manufacturerId) throws ManufacturerNotFoundException, ModelNotFoundException {
        List<Model> allModels = modelTrimService.getModelsByManufacturerId(manufacturerId);
        if (allModels.size() > 0) {
            return new ResponseEntity<>(allModels, HttpStatus.OK);
        } else {
            throw new ModelNotFoundException("No model found for this manufacturer id " + manufacturerId);
        }
    }
}
