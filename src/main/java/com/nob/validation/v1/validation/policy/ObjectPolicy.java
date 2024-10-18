package com.nob.validation.v1.validation.policy;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Policy for object validation
 * */
@Data
@AllArgsConstructor
public class ObjectPolicy {
    private String objectName;
    private List<FieldPolicy> fieldPolicies;

    public ObjectPolicy() {

    }
}
