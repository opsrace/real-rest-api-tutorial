package com.api.tutorials.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Forbidden Access")
public class ForbiddenException extends ApplicationException {
    public ForbiddenException(String message) {
        super(4101, message);
    }
}
