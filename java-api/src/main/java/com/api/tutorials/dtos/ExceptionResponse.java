package com.api.tutorials.dtos;
import com.api.tutorials.exceptions.ApplicationException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse {
    int code;
    String message;

    public static ExceptionResponse of(int errorCode, String errorMessage) {
        ExceptionResponse response = new ExceptionResponse();
        response.setCode(errorCode);
        response.setMessage(errorMessage);

        return response;
    }

    public static ExceptionResponse of(ApplicationException exception) {
        return of(exception.getCode(), exception.getMessage());
    }
}