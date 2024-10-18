package com.nob.validation.v1.configuration;

import com.nob.validation.v1.enumerate.Operation;
import lombok.Data;

import java.util.List;

@Data
public class RuleConfiguration {
    private String condition;
    private String simpleType;
    private String expression;
    private Operation operation;
    private List<Object> comparedValues;
    private String message;
    private ObjectConfiguration innerConfig;
}
