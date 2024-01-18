package com.api.tutorials.controllers;

import com.api.tutorials.apis.CarApi;
import com.api.tutorials.dtos.*;
import com.api.tutorials.services.CarService;
import com.api.tutorials.utility.ValueUtils;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.api.tutorials.services.mappers.CarMapper.toListRequest;

@Slf4j
@RestController
public class CarController implements CarApi {

    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @Override
    public List<Car> lists(
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "recordPerPage", required = false) Integer recordPerPage,
            @RequestParam(value = "model", required = false) List<String> models,
            @RequestParam(value = "make", required = false) List<String> makes,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "sortOrder", required = false) String sortOrder
    ) {

        log.info("Passed makes are: {}", makes);
        log.info("Passed models are: {}", models);

        return service.search(toListRequest(pageNo, recordPerPage, models, makes, sortBy, sortOrder));
    }



    @PostMapping("/search")
    public List<Car> search(@RequestBody CarListRequest request) {
        log.info("Search params are {}", request);

        return service.search(request);
    }

    @Override
    public Car findById(String carId) {

        return service.findById(carId);
    }

    @Override
    public Car create(Car car) {

        return service.create(car);
    }

    @Override
    public Car update(String carId, Car car) {
        car.setId(carId);

        return service.update(car);
    }

    @Override
    public Car partialUpdate(String carId, JsonPatch patch) {

        return service.partialUpdate(carId, patch);
    }


    @Override
    public BooleanResponse delete(String carId) {

        return service.delete(carId);
    }
}
