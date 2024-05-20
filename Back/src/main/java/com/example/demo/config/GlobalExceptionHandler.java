package com.example.demo.config;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errorList = fieldErrors.stream()
                .map(error -> error.getField() + ", " + error.getDefaultMessage())
                .toList();
        return new ResponseEntity<>(new ErrorMessage(errorList), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handlerConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> set = ex.getConstraintViolations();
        List<String> errorList = set.stream()
                .map(error -> error.getPropertyPath() + ", " + error.getMessage())
                .toList();
        return new ResponseEntity<>(new ErrorMessage(errorList), HttpStatus.BAD_REQUEST);
    }
}
