package com.example.demo.config.exceotion;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errorMessage = fieldErrors.stream()
                .map(error -> error.getField() + ", " + error.getDefaultMessage())
                .toList();
        errorMessage.forEach(s -> log.error("Worked MethodArgumentNotValidException -> " + s));
        return new ResponseEntity<>(new ErrorMessage(errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handlerConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> set = ex.getConstraintViolations();
        List<String> errorMessage = set.stream()
                .map(error -> error.getPropertyPath() + ", " + error.getMessage())
                .toList();
        errorMessage.forEach(s -> log.error("Worked ConstraintViolationException -> " + s));
        return new ResponseEntity<>(new ErrorMessage(errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<ErrorMessage> handlerFileException(FileException ex) {
        List<String> errorMessage = new ArrayList<>();
        errorMessage.add(ex.getMessage());
        errorMessage.forEach(s -> log.error("Worked FileException -> " + s));
        return new ResponseEntity<>(new ErrorMessage(errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFountDataException.class)
    public ResponseEntity<ErrorMessage> handlerNotFountDataException(NotFountDataException ex) {
        List<String> errorMessage = new ArrayList<>();
        errorMessage.add(ex.getMessage());
        errorMessage.forEach(s -> log.error("Worked NotFountDataException -> " + s));
        return new ResponseEntity<>(new ErrorMessage(errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<ErrorMessage> handlerDuplicateDataException(DuplicateDataException ex) {
        List<String> errorMessage = new ArrayList<>();
        errorMessage.add(ex.getMessage());
        errorMessage.forEach(s -> log.error("Worked DuplicateDataException -> " + s));
        return new ResponseEntity<>(new ErrorMessage(errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidContentTypeException.class)
    public ResponseEntity<ErrorMessage> handlerInvalidContentTypeException(InvalidContentTypeException ex) {
        List<String> errorMessage = new ArrayList<>();
        errorMessage.add("데이터 형식이 잘못되었습니다.");
        errorMessage.forEach(s -> log.error("Worked InvalidContentTypeException -> " + s));
        return new ResponseEntity<>(new ErrorMessage(errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorMessage> handlerMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        List<String> errorMessage = new ArrayList<>();
        errorMessage.add("파일 크기가 너무 큽니다.");
        errorMessage.forEach(s -> log.error("Worked MaxUploadSizeExceededException -> " + s));
        return new ResponseEntity<>(new ErrorMessage(errorMessage), HttpStatus.BAD_REQUEST);
    }
}
