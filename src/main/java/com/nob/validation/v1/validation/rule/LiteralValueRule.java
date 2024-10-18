package com.nob.validation.v1.validation.rule;

import com.nob.validation.v1.enumerate.FieldType;
import com.nob.validation.v1.enumerate.Operation;

/**
 * Rule for literal value property of validated object
 * */
public interface LiteralValueRule extends Rule {

    /**
     * Type of value
     * */
    FieldType getFieldType();

    /**
     * Operation perform on value
     * */
    Operation getOperation();
}
