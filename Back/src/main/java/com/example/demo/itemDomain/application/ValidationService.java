package com.example.demo.itemDomain.application;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ValidationService {
    public <T> void checkValid(T validactionTarget) {}
}
