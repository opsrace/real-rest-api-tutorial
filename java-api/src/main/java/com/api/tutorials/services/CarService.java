package com.api.tutorials.services;

import com.api.tutorials.dtos.BooleanResponse;
import com.api.tutorials.dtos.Car;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.List;

public interface CarService {
    List<Car> list();
    Car findById(Long id);
    Car create(Car car);
    Car update(Car car);
    BooleanResponse delete(Long id);
    Car partialUpdate(Long id, JsonPatch patch);

    Car updateRegistration(String carId, Car car);

    Car updateMake(String carId, Car car);

    Car updateModel(String carId, Car car);
}
