package com.api.tutorials.dao;

import com.api.tutorials.dao.beans.CarDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarRepository extends MongoRepository<CarDocument, String> {
}
