package com.api.tutorials.controllers;

import com.api.tutorials.apis.CarApi;
import com.api.tutorials.dtos.BooleanResponse;
import com.api.tutorials.dtos.Car;
import com.api.tutorials.services.CarService;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.api.tutorials.utility.ValueUtils.toLongValue;

@Slf4j
@RestController
public class CarController implements CarApi {

    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @Override
    public List<Car> list() {

        return service.list();
    }

    @Override
    public Car findById(String carId) {
        Long carIdNumber = toLongValue(carId);

        return service.findById(carIdNumber);
    }

    @Override
    public Car create(Car car) {

        return service.create(car);
    }

    @Override
    public Car update(String carId, Car car) {
        car.setId(toLongValue(carId));

        return service.update(car);
    }

    @Override
    public Car partialUpdate(String carId, JsonPatch patch) {
        Long carIdNumber = toLongValue(carId);

        return service.partialUpdate(carIdNumber, patch);
    }


    @Override
    public BooleanResponse delete(String carId) {
        Long carIdNumber = toLongValue(carId);

        return service.delete(carIdNumber);
    }
}
