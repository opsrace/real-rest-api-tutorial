package com.api.tutorials.services.validators;

import com.api.tutorials.common.ApplicationErrors;
import com.api.tutorials.dtos.Car;

import static com.api.tutorials.utility.ValidationUtils.validateString;

public class CarValidator {
    public static void validateCar(Car car) {
        validateString(car.getRegistrationNumber(), ApplicationErrors.MISSING_REGISTRATION_NUMBER);
        validateString(car.getMake(), ApplicationErrors.MISSING_MAKE_OF_CAR);
        validateString(car.getModel(), ApplicationErrors.MISSING_MODEL_OF_CAR);
    }

    public static void validateCarAndId(Car car) {
        validateId(car.getId());
        validateCar(car);
    }

    public static void validateId(String id) {
        validateString(id, ApplicationErrors.MISSING_REQUIRED_ID);
    }
}
