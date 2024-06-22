package com.example.demo.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Status {
    FALSE("false"),
    TRUE("true");

    private final String status;
}
