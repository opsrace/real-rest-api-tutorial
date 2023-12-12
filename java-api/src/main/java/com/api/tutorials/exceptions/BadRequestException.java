package com.api.tutorials.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Bad input request")
public class BadRequestException extends ApplicationException {
    public BadRequestException(String message) {
        this(3101, message);
    }

    public BadRequestException(int code, String message) {
        super(code, message);
    }
}
