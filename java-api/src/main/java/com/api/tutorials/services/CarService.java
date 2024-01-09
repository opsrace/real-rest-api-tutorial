package com.api.tutorials.services;

import com.api.tutorials.dtos.BooleanResponse;
import com.api.tutorials.dtos.Car;
import com.api.tutorials.dtos.CarListRequest;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.List;

public interface CarService {
    List<Car> list();
    Car findById(String id);
    Car create(Car car);
    Car update(Car car);
    BooleanResponse delete(String id);
    Car partialUpdate(String id, JsonPatch patch);

    List<Car> search(CarListRequest request);
}
