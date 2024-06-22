package com.example.demo.config.exceotion;

public class DuplicateDataException extends RuntimeException {
    public DuplicateDataException() {}
    public DuplicateDataException(String takenMessage) {
        super(takenMessage);
    }
}
