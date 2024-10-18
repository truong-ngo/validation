package com.nob.validation.v1.configuration;

import com.nob.validation.v1.enumerate.FieldType;
import lombok.Data;

import java.util.List;

@Data
public class FieldConfiguration {
    private String fieldName;
    private FieldType fieldType;
    private List<RuleConfiguration> rules;
}
