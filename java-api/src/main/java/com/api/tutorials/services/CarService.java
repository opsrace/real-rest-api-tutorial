package com.api.tutorials.services;

import com.api.tutorials.dao.CarRepository;
import com.api.tutorials.dao.beans.CarDocument;
import com.api.tutorials.dtos.Car;
import com.api.tutorials.exceptions.RecordNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    private final CarRepository repository;

    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    public Car add(Car car) {
        CarDocument document = toCarDocument(car);
        return toCarDto(repository.save(document));
    }
    
    public Car findById(String id) {
      CarDocument doc = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Car not found : "+ id));
      Car car = toCarDto(doc);

      return car;
    }

    private Car toCarDto(CarDocument carDoc) {
        Car car = new Car();
        car.setId(carDoc.getId());
        car.setMake(carDoc.getMake());
        car.setModel(carDoc.getModel());
        car.setRegistrationNumber(carDoc.getRegistrationNumber());
        car.setFeatures(carDoc.getFeatures());

        return car;
    }

    private CarDocument toCarDocument(Car car) {
        CarDocument doc = new CarDocument();
        doc.setMake(car.getMake());
        doc.setModel(car.getModel());
        doc.setRegistrationNumber(car.getRegistrationNumber());
        doc.setFeatures(car.getFeatures());

        return doc;
    }
}
