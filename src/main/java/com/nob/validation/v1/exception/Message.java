package com.nob.validation.v1.exception;

public class Message {
    public static String REQUIRED = "Field is required!";
    public static String NOT_EMPTY = "Field is not empty!";
    public static String NOT_APPLICABLE = "Not applicable!";
    public static String INVALID_EXPRESSION_FORMAT = "Invalid expression: %s!";

    public static String invalidExpression(String expression) {
        return String.format(INVALID_EXPRESSION_FORMAT, expression);
    }
}
