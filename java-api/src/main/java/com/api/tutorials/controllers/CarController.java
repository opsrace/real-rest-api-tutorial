package com.api.tutorials.controllers;

import com.api.tutorials.apis.CarApi;
import com.api.tutorials.dtos.BooleanResponse;
import com.api.tutorials.dtos.Car;
import com.api.tutorials.dtos.CarListRequest;
import com.api.tutorials.services.CarService;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/list")
    //GET /cars?make=Alsvin,Honda&id_min=100&id_max=200&sort=make:desc,model:asc
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

        return service.list();
    }

    @PostMapping("/search")
    public List<Car> search(CarListRequest request) {
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
