package com.api.tutorials.dao.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document("cars")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarDocument {
    @Id
    private String id;
    private String registrationNumber;
    private String make;
    private String model;
    private List<String> features = new ArrayList<>();
}