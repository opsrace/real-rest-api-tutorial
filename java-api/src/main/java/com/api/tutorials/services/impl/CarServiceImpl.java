package com.api.tutorials.services.impl;

import com.api.tutorials.dao.CarRepository;
import com.api.tutorials.dao.beans.CarDocument;
import com.api.tutorials.dtos.BooleanResponse;
import com.api.tutorials.dtos.Car;
import com.api.tutorials.dtos.CarListRequest;
import com.api.tutorials.exceptions.BadRequestException;
import com.api.tutorials.exceptions.RecordNotFoundException;
import com.api.tutorials.services.CarService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.api.tutorials.services.mappers.CarMapper.*;
import static com.api.tutorials.services.validators.CarValidator.validateCarAndId;
import static com.api.tutorials.services.validators.CarValidator.validateId;

@Service
public class CarServiceImpl implements CarService {
    private static Long id = 0L;
    private final CarRepository repository;
    public CarServiceImpl(CarRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Car> list() {

        return toDtoList(repository.findAll());
    }

    @Override
    public List<Car> search(CarListRequest request) {
        return list();
    }

    @Override
    public Car findById(String carId) {

        return toDto(fetchById(carId));
    }

    private CarDocument fetchById(String carId) {

        return repository.findById(carId).orElseThrow(() -> new RecordNotFoundException("Car not found"));
    }

    @Override
    public Car create(Car car) {

        return toDto(repository.save(toDocument(car)));
    }

    @Override
    public Car update(Car car) {
        validateCarAndId(car);

        CarDocument existingDocument = fetchById(car.getId());
        existingDocument.setFeatures(car.getFeatures());
        existingDocument.setMake(car.getMake());
        existingDocument.setModel(car.getModel());
        existingDocument.setRegistrationNumber(car.getRegistrationNumber());
        repository.save(existingDocument);

        return toDto(existingDocument);
    }

    @Override
    public BooleanResponse delete(String id) {
        validateId(id);

        if (!repository.existsById(id)) {
            throw new RecordNotFoundException("Car not found");
        }
        repository.deleteById(id);

        return BooleanResponse.success();
    }

    @Override
    public Car partialUpdate(String id, JsonPatch patch) {
        validateId(id);

        CarDocument car = fetchById(id);
        CarDocument patchedCar;
        try {
            patchedCar = applyPatch(car, patch);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        repository.save(patchedCar);

        return toDto(patchedCar);
    }

    private CarDocument applyPatch(CarDocument car, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(car, JsonNode.class));

        return objectMapper.treeToValue(patched, CarDocument.class);
    }
}
