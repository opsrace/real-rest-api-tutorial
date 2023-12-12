package com.api.tutorials.exceptions;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private int code;

    public ApplicationException(int code, String message) {
        super(message);
        this.code = code;
    }
}
