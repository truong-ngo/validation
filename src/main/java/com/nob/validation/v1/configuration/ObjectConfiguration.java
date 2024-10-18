package com.nob.validation.v1.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObjectConfiguration {
    private String objectName;
    private List<FieldConfiguration> fieldConfigurations;
}
