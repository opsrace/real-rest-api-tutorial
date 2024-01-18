package com.api.tutorials.services.mappers;

import com.api.tutorials.dao.beans.CarDocument;
import com.api.tutorials.dtos.*;
import com.api.tutorials.utility.ValueUtils;

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

    public static CarListRequest toListRequest(Integer pageNo, Integer recordPerPage, List<String> models, List<String> makes, String sortBy, String sortOrder) {
        CarListRequest request = new CarListRequest();
        request.setPageNumber(pageNo);
        request.setRecordPerPage(recordPerPage);
        if (!ValueUtils.isEmpty(models)) {
            request.getFilters().add(new ListFilter("model", models, ListOperator.OR));
        }
        if (!ValueUtils.isEmpty(makes)) {
            request.getFilters().add(new ListFilter("makes", makes, ListOperator.OR));
        }
        if (!ValueUtils.isEmpty(sortBy)) {
            request.setSorting(new ArrayList<>());
            request.getSorting().add(new ListSort(sortBy, "ASC".equals(sortOrder)));
        }

        return request;
    }
}
