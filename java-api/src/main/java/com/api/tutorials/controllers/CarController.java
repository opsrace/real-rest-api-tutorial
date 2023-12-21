package com.api.tutorials.controllers;

import com.api.tutorials.dtos.BooleanResponse;
import com.api.tutorials.dtos.Car;
import com.api.tutorials.services.CarService;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.api.tutorials.utility.ValueUtils.toLongValue;

@Slf4j
@RestController
@RequestMapping("/api/v1/cars")
public class CarController {

    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @GetMapping
    public List<Car> list() {

        return service.list();
    }

    @GetMapping("/{carId}")
    public Car findById(@PathVariable("carId") String carId) {
        Long carIdNumber = toLongValue(carId);

        return service.findById(carIdNumber);
    }

    @PostMapping
    public Car create(@RequestBody Car car) {

        return service.create(car);
    }

    @PutMapping("/{carId}")
    public Car update(@PathVariable("carId") String carId, @RequestBody Car car) {
        car.setId(toLongValue(carId));

        return service.update(car);
    }

    @PatchMapping("/{carId}")
    public Car partialUpdate(@PathVariable("carId") String carId, @RequestBody JsonPatch patch) {
        Long carIdNumber = toLongValue(carId);

        return service.partialUpdate(carIdNumber, patch);
    }


    @DeleteMapping("/{carId}")
    public BooleanResponse delete(@PathVariable("carId") String carId) {
        Long carIdNumber = toLongValue(carId);

        return service.delete(carIdNumber);
    }

    @Deprecated
    @PatchMapping("/{carId}/update-registration")
    public Car updateRegistration(@PathVariable("carId") String carId, @RequestBody Car car) {
        return service.updateRegistration(carId, car);
    }

    @Deprecated
    @PatchMapping("/{carId}/update-make")
    public Car updateMake(@PathVariable("carId") String carId, @RequestBody Car car) {
        return service.updateMake(carId, car);

    }

    @Deprecated
    @PatchMapping("/{carId}/update-model")
    public Car updateModel(@PathVariable("carId") String carId, @RequestBody Car car) {
        return service.updateModel(carId, car);
    }
}
