package com.api.tutorials.controllers;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/cars")
public class CarController {

    private static Long id = 0L;
    Map<Long, Car> cars = new HashMap<>();

    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @GetMapping
    public List<Car> list() {

        return new ArrayList<>(cars.values());
    }

    @PostMapping
    public Car add(@RequestBody Car car) {

        return service.add(car);
    }

    @GetMapping("/{carId}")
    public Car findById(@PathVariable("carId") String carId) {

        return service.findById(carId);
    }

    @PutMapping("/{carId}")
    public Car edit(@PathVariable("carId") String carId, @RequestBody Car car) {
       /* Long carIdNumber = toLong(carId);
        car.setId(carIdNumber);

        if (!cars.containsKey(carIdNumber)) {
            throw new RecordNotFoundException("Car not found");
        } else if (null == car.getRegistrationNumber() || car.getRegistrationNumber().isEmpty()) {
            throw new BadRequestException("Car registration number cannot be null");
        }


        cars.put(car.getId(), car); */
        return car;
    }

    @PatchMapping("/{carId}")
    public Car partialUpdate(@PathVariable("carId") String carId, @RequestBody JsonPatch patch) {
        Long carIdNumber = toLong(carId);

        if (!cars.containsKey(carIdNumber)) {
            throw new RecordNotFoundException("Car not found");
        }

        Car car = cars.get(carIdNumber);
        Car patchedCar;
        try {
            patchedCar = applyPatch(car, patch);
        }catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

        //cars.put(car.getId(), patchedCar);
        return patchedCar;
    }

    private Car applyPatch(Car car, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(car, JsonNode.class));

        return objectMapper.treeToValue(patched, Car.class);
    }

    @DeleteMapping("/{carId}")
    public BooleanResponse delete(@PathVariable("carId") String carId) {
        Long carIdNumber = toLong(carId);

        if (!cars.containsKey(carIdNumber)) {
            throw new RecordNotFoundException("Car not found");
        }

        cars.remove(carIdNumber);
        return BooleanResponse.success();
    }

    @PatchMapping("/{carId}/update-registration")
    public Car updateRegistration(@PathVariable("carId") String carId, @RequestBody Car car) {
        Long carIdNumber = toLong(carId);

        if (!cars.containsKey(carIdNumber)) {
            throw new RecordNotFoundException("Car not found");
        }

        cars.get(carIdNumber).setRegistrationNumber(car.getRegistrationNumber());

        return cars.get(carIdNumber);
    }

    @PatchMapping("/{carId}/update-make")
    public Car updateMake(@PathVariable("carId") String carId, @RequestBody Car car) {
        Long carIdNumber = toLong(carId);

        if (!cars.containsKey(carIdNumber)) {
            throw new RecordNotFoundException("Car not found");
        }

        cars.get(carIdNumber).setMake(car.getMake());

        return cars.get(carIdNumber);
    }

    @PatchMapping("/{carId}/update-model")
    public Car updateModel(@PathVariable("carId") String carId, @RequestBody Car car) {
        Long carIdNumber = toLong(carId);

        if (!cars.containsKey(carIdNumber)) {
            throw new RecordNotFoundException("Car not found");
        }

        cars.get(carIdNumber).setModel(car.getModel());

        return cars.get(carIdNumber);
    }

    private Long toLong(String strValue) {
        if (null == strValue) {
            throw new BadRequestException("Null value passed");
        }

        Long longValue;
        try {
            longValue = Long.valueOf(strValue);
        } catch (NumberFormatException exception) {
            throw new BadRequestException(3106, "Id is not valid positive integer");
        }

        return longValue;
    }
}
