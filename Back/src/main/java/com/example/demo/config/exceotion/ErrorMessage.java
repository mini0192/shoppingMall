package com.example.demo.config.exceotion;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorMessage {
    private List<String> error;
}
