package com.api.tutorials.services.mappers;

import com.api.tutorials.dao.beans.CarDocument;
import com.api.tutorials.dtos.Car;

import java.util.ArrayList;
import java.util.List;

public class CarMapper {
    public static Car toDto(CarDocument carDoc) {
        Car car = new Car();
        car.setId(carDoc.getId());
        car.setMake(carDoc.getMake());
        car.setModel(carDoc.getModel());
        car.setRegistrationNumber(carDoc.getRegistrationNumber());
        car.setFeatures(carDoc.getFeatures());

        return car;
    }

    public static List<Car> toDtoList(List<CarDocument> all) {
        List<Car> list = new ArrayList<>();
        for (CarDocument doc: all) {
            list.add(toDto(doc));
        }

        return list;
    }

    public static CarDocument toDocument(Car car) {
        CarDocument doc = new CarDocument();
        doc.setMake(car.getMake());
        doc.setModel(car.getModel());
        doc.setRegistrationNumber(car.getRegistrationNumber());
        doc.setFeatures(car.getFeatures());

        return doc;
    }
}
