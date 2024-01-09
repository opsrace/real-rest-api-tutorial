package com.api.tutorials.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private String id;
    private String registrationNumber;
    private String make;
    private String model;
    private List<String> features = new ArrayList<>();
}
