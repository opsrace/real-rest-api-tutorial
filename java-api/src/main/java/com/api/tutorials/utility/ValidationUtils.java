package com.api.tutorials.utility;

import ch.qos.logback.core.spi.ErrorCodes;
import com.api.tutorials.common.ApplicationErrors;
import com.api.tutorials.exceptions.BadRequestException;

import static com.api.tutorials.utility.ValueUtils.isEmpty;

public class ValidationUtils {
    public static void validateString(String value) {
        validateString(value, ApplicationErrors.MISSING_REQUIRED_STRING);
    }
    public static void validateString(String value, int code) {
        validateString(value,"Missing required string value", code);
    }
    public static void validateString(String value, String message) {
        validateString(value, message, ApplicationErrors.MISSING_REQUIRED_STRING);
    }

    public static void validateString(String value, String message, int code) {
        if (isEmpty(value)) {
            throw new BadRequestException(code, message);
        }
    }
}
