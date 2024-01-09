package com.api.tutorials.services.impl;

import com.api.tutorials.dao.CarRepository;
import com.api.tutorials.dao.beans.CarDocument;
import com.api.tutorials.dtos.*;
import com.api.tutorials.exceptions.BadRequestException;
import com.api.tutorials.exceptions.RecordNotFoundException;
import com.api.tutorials.services.CarService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.util.List;

import static com.api.tutorials.services.mappers.CarMapper.*;
import static com.api.tutorials.services.validators.CarValidator.validateCarAndId;
import static com.api.tutorials.services.validators.CarValidator.validateId;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository repository;
    private final MongoTemplate mongoTemplate;
    public CarServiceImpl(CarRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Car> list() {

        return toDtoList(repository.findAll());
    }

    @Override
    public List<Car> search(CarListRequest request) {
        Query query = new Query();

        // Apply filters
        if (request.getFilters() != null) {
            for (ListFilter filter : request.getFilters()) {
                Criteria criteria = Criteria.where(filter.getField());
                switch (filter.getOperator()) {
                    case OR:
                    case IN:
                        criteria.in(filter.getValues());
                        break;
                    // Implement other operators as needed
                    // Handle GREATER, LESSER, AND, OR, NOT, etc.
                    default:
                        break;
                }
                query.addCriteria(criteria);
            }
        }

        // Apply sorting
        if (request.getSorting() != null) {
            for (ListSort sort : request.getSorting()) {
                Sort.Direction direction = sort.getAscending() ? Sort.Direction.ASC : Sort.Direction.DESC;
                query.with(Sort.by(direction, sort.getField()));
            }
        }

        // Paginate results
        PageRequest pageRequest = PageRequest.of(request.getPageNumber() - 1, request.getRecordPerPage());
        query.with(pageRequest);

        // Fetch data from MongoDB using the constructed query
        List<CarDocument> carDocuments = mongoTemplate.find(query, CarDocument.class);

        return toDtoList(carDocuments);
    }

    @Override
    public Car findById(String carId) {

        return toDto(fetchById(carId));
    }

    private CarDocument fetchById(String carId) {

        return repository.findById(carId).orElseThrow(() -> new RecordNotFoundException("Car not found"));
    }

    @Override
    public Car create(Car car) {

        return toDto(repository.save(toDocument(car)));
    }

    @Override
    public Car update(Car car) {
        validateCarAndId(car);

        CarDocument existingDocument = fetchById(car.getId());
        existingDocument.setFeatures(car.getFeatures());
        existingDocument.setMake(car.getMake());
        existingDocument.setModel(car.getModel());
        existingDocument.setRegistrationNumber(car.getRegistrationNumber());
        repository.save(existingDocument);

        return toDto(existingDocument);
    }

    @Override
    public BooleanResponse delete(String id) {
        validateId(id);

        if (!repository.existsById(id)) {
            throw new RecordNotFoundException("Car not found");
        }
        repository.deleteById(id);

        return BooleanResponse.success();
    }

    @Override
    public Car partialUpdate(String id, JsonPatch patch) {
        validateId(id);

        CarDocument car = fetchById(id);
        CarDocument patchedCar;
        try {
            patchedCar = applyPatch(car, patch);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        repository.save(patchedCar);

        return toDto(patchedCar);
    }

    private CarDocument applyPatch(CarDocument car, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(car, JsonNode.class));

        return objectMapper.treeToValue(patched, CarDocument.class);
    }
}
