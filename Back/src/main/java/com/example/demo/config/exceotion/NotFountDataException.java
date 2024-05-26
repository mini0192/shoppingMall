package com.example.demo.config.exceotion;

public class NotFountDataException extends RuntimeException{
    public NotFountDataException() {}
    public NotFountDataException(String message) {
        super(message);
    }
}
