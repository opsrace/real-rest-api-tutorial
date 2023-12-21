package com.api.tutorials.services.impl;

import com.api.tutorials.dtos.BooleanResponse;
import com.api.tutorials.dtos.Car;
import com.api.tutorials.exceptions.BadRequestException;
import com.api.tutorials.exceptions.RecordNotFoundException;
import com.api.tutorials.services.CarService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.api.tutorials.services.validators.CarValidator.validateCar;
import static com.api.tutorials.utility.ValueUtils.isEmpty;
import static org.apache.commons.lang3.math.NumberUtils.toLong;

@Service
public class CarServiceImpl implements CarService {
    private static Long id = 0L;
    Map<Long, Car> cars = new HashMap<>();

    public CarServiceImpl() {
        //initialize few cars
        cars.put(++id, new Car(id, "LAX 001", "Honda", "City", List.of("Manual")));
        cars.put(++id, new Car(id, "LAX 002", "Honda", "Civic", List.of("Automatic")));
        cars.put(++id, new Car(id, "LAX 003", "Honda", "Accord", List.of("Automatic", "Electric")));
        cars.put(++id, new Car(id, "LAX 004", "Honda", "Lumiere", List.of("DCT")));
    }

    @Override
    public List<Car> list() {
        return new ArrayList<>(cars.values());
    }

    @Override
    public Car findById(Long carId) {

        if (!cars.containsKey(carId)) {
            throw new RecordNotFoundException("Car not found");
        }

        return cars.get(carId);
    }

    @Override
    public Car create(Car car) {
        validateCar(car);
        car.setId(++id);
        cars.put(car.getId(), car);

        return car;
    }

    @Override
    public Car update(Car car) {

        if (!cars.containsKey(car.getId())) {
            throw new RecordNotFoundException("Car not found");
        } else if (isEmpty(car.getRegistrationNumber())) {
            throw new BadRequestException("Car registration number cannot be null");
        }
        cars.put(car.getId(), car);

        return car;
    }

    @Override
    public BooleanResponse delete(Long id) {
        if (!cars.containsKey(id)) {
            throw new RecordNotFoundException("Car not found");
        }
        cars.remove(id);

        return BooleanResponse.success();
    }

    @Override
    public Car partialUpdate(Long id, JsonPatch patch) {
        if (!cars.containsKey(id)) {
            throw new RecordNotFoundException("Car not found");
        }

        Car car = cars.get(id);
        Car patchedCar;
        try {
            patchedCar = applyPatch(car, patch);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

        cars.put(car.getId(), patchedCar);
        return patchedCar;
    }

    private Car applyPatch(Car car, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(car, JsonNode.class));

        return objectMapper.treeToValue(patched, Car.class);
    }

    @Override
    public Car updateRegistration(String carId, Car car) {
        Long carIdNumber = toLong(carId);

        if (!cars.containsKey(carIdNumber)) {
            throw new RecordNotFoundException("Car not found");
        }

        cars.get(carIdNumber).setRegistrationNumber(car.getRegistrationNumber());

        return cars.get(carIdNumber);
    }

    @Override
    public Car updateMake(String carId, Car car) {
        Long carIdNumber = toLong(carId);

        if (!cars.containsKey(carIdNumber)) {
            throw new RecordNotFoundException("Car not found");
        }

        cars.get(carIdNumber).setMake(car.getMake());

        return cars.get(carIdNumber);
    }

    @Override
    public Car updateModel(String carId, Car car) {
        Long carIdNumber = toLong(carId);

        if (!cars.containsKey(carIdNumber)) {
            throw new RecordNotFoundException("Car not found");
        }

        cars.get(carIdNumber).setModel(car.getModel());

        return cars.get(carIdNumber);
    }

}
