package com.example.demo.application;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Validated
public class ValidationService {
    public <T> void checkValid(T validactionTarget) {}
}
