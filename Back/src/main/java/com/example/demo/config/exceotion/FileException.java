package com.example.demo.config.exceotion;

public class FileException extends RuntimeException {
    public FileException() {}
    public FileException(String takenMessage) {
        super(takenMessage);
    }
}
