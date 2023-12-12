package com.api.tutorials.exceptions;

import com.api.tutorials.dtos.ExceptionResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ExceptionResponse> handleError404(ServletException exception, HttpServletRequest request) {
        return new ResponseEntity<>(getServletExceptionResponse(exception, request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ExceptionResponse> handleAllExceptions(Exception exception) {

       // log.error("Exception occurred: {}", exception.getMessage());

        if (exception instanceof ApplicationException appException) {
            return handleApplicationException(appException);
        } else if (exception instanceof HttpMessageNotReadableException notReadableException) {
            return handleFormattingException(notReadableException);
        }
        logger.error("Global exception handler", exception);
        return new ResponseEntity<>(ExceptionResponse.of(302, "Unknown error occurred please contact site admin"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ExceptionResponse> handleFormattingException(HttpMessageNotReadableException exception) {
        String message;

        if (exception.getCause() instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException) {
           exception.printStackTrace();

            message = getEnumMessage(exception.getMessage());
        } else {
            message = "Parser error, please make sure JSON is properly formatted";
        }

        return new ResponseEntity<>(ExceptionResponse.of(303,
                message),
                HttpStatus.BAD_REQUEST);
    }

    private String getEnumMessage(String message) {
        if (message.contains("not one of the values accepted for Enum class")) {
            return "Enum value Parsing error, Please make sure correct type is passed";
        }

        return "Parsing error, Date/Number/Enum format might not be correct";
    }

    protected ResponseEntity<ExceptionResponse> handleApplicationException(ApplicationException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (exception instanceof RecordNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(ExceptionResponse.of(exception), status);
    }

    private ExceptionResponse getServletExceptionResponse(ServletException exception, HttpServletRequest request) {
        ExceptionResponse response;
        if (exception instanceof NoHandlerFoundException) {
            response = ExceptionResponse.of(304,
                    "END POINT not found " + request.getRequestURI());
        } else {
            response = ExceptionResponse.of(305,
                    "HTTP Method Not supported: " + request.getMethod());
        }

        return response;
    }

}