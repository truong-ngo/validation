package com.nob.validation.v1.enumerate;

import lombok.Getter;

@Getter
public enum Operation {

    EQUAL("EQUAL", FieldType.of(FieldType.STRING, FieldType.INTEGER, FieldType.DECIMAL, FieldType.BOOLEAN)),
    EQUAL_IGNORE_CASE("EQUAL_IGNORE_CASE", FieldType.of(FieldType.STRING)),
    GREATER("GREATER", FieldType.of(FieldType.INTEGER, FieldType.DECIMAL)),
    GREATER_EQUAL("GREATER_EQUAL", FieldType.of(FieldType.INTEGER, FieldType.DECIMAL)),
    LESSER("LESSER", FieldType.of(FieldType.INTEGER, FieldType.DECIMAL)),
    LESSER_EQUAL("LESSER_EQUAL", FieldType.of(FieldType.DECIMAL, FieldType.INTEGER)),
    BETWEEN("BETWEEN", FieldType.of(FieldType.INTEGER, FieldType.DECIMAL)),
    STARTS_WITH("STARTS_WITH", FieldType.of(FieldType.STRING)),
    ENDS_WITH("ENDS_WITH", FieldType.of(FieldType.STRING)),
    CONTAINS("CONTAINS", FieldType.of(FieldType.STRING)),
    PATTERN("PATTERN", FieldType.of(FieldType.STRING)),
    LENGTH("LENGTH", FieldType.of(FieldType.STRING, FieldType.COLLECTION)),
    MAX_LENGTH("MAX_LENGTH", FieldType.of(FieldType.STRING, FieldType.COLLECTION)),
    MIN_LENGTH("MAX_LENGTH", FieldType.of(FieldType.STRING, FieldType.COLLECTION));

    private final String name;
    private final FieldType[] appliedTypes;

    Operation(String name, FieldType[] appliedTypes) {
        this.name = name;
        this.appliedTypes = appliedTypes;
    }
}
