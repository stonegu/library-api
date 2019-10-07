package com.ultimatesoftware.libraryapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> customClientException(Exception ex, WebRequest request) {
        ApiError error = new ApiError(((ResponseStatusException)ex).getStatus().value(), LocalDateTime.now(), ((ResponseStatusException)ex).getReason());

        return new ResponseEntity<>(error, HttpStatus.resolve(error.getStatus()));
    }


}
