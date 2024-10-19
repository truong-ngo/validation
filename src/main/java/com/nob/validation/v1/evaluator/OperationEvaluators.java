package com.nob.validation.v1.evaluator;

import com.nob.validation.v1.enumerate.FieldType;
import com.nob.validation.v1.enumerate.Operation;
import com.nob.validation.v1.exception.ValidationException;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

public class OperationEvaluators {

    /**
     * Handler the evaluation of rule. Delegate to specific operation executor
     * @param operation the operation perform
     * @param operand the field / property
     * @param arguments the value compare
     * @return true if rule is valid
     * */
    public static boolean execute(Operation operation, Object operand, List<Object> arguments) {
        return switch (operation) {
            case EQUAL -> equal.execute(operand, arguments);
            case NOT_EQUAL -> notEqual.execute(operand, arguments);
            case EQUAL_IGNORE_CASE -> equalIgnoreCase.execute(operand, arguments);
            case NOT_EQUAL_IGNORE_CASE -> notEqualIgnoreCase.execute(operand, arguments);
            case GREATER -> greater.execute(operand, arguments);
            case GREATER_EQUAL -> greaterEqual.execute(operand, arguments);
            case LESSER -> lesser.execute(operand, arguments);
            case LESSER_EQUAL -> lesserEqual.execute(operand, arguments);
            case BETWEEN -> between.execute(operand, arguments);
            case STARTS_WITH -> startWith.execute(operand, arguments);
            case ENDS_WITH -> endWith.execute(operand, arguments);
            case CONTAINS -> contains.execute(operand, arguments);
            case PATTERN -> pattern.execute(operand, arguments);
            case LENGTH -> length.execute(operand, arguments);
            case MAX_LENGTH -> maxLength.execute(operand, arguments);
            case MIN_LENGTH -> minLength.execute(operand, arguments);
        };
    }

    /**
     * Equal operation evaluator
     * */
    public static OperationEvaluator equal = (operand, arguments) -> {
        checkSupportedType(FieldType.ofValue(operand), Operation.EQUAL);
        Object comparedValue = arguments.get(0);
        FieldType fieldType = FieldType.ofValue(operand);
        if (FieldType.isString(fieldType)) {
            String string = (String) operand;
            String compareString = (String) comparedValue;
            return string.equals(compareString);
        }
        if (FieldType.isInteger(fieldType)) {
            BigInteger integer = new BigInteger(String.valueOf(operand));
            BigInteger compareInteger = new BigInteger(String.valueOf(comparedValue));
            return integer.equals(compareInteger);
        }
        if (FieldType.isDecimal(fieldType)) {
            BigDecimal bigDecimal = new BigDecimal(String.valueOf(operand));
            BigDecimal compareBigDecimal = new BigDecimal(String.valueOf(comparedValue));
            return bigDecimal.equals(compareBigDecimal);
        }
        if (FieldType.isDate(fieldType)) {

        }
        Boolean booleanValue = (Boolean) operand;
        Boolean compareBoolean = (Boolean) comparedValue;
        return booleanValue.equals(compareBoolean);
    };

    /**
     * Not equal operation evaluator
     * */
    public static OperationEvaluator notEqual = (operand, arguments) -> {
        checkSupportedType(FieldType.ofValue(operand), Operation.NOT_EQUAL);
        Object comparedValue = arguments.get(0);
        FieldType fieldType = FieldType.ofValue(operand);
        if (FieldType.isString(fieldType)) {
            String string = (String) operand;
            String compareString = (String) comparedValue;
            return !string.equals(compareString);
        }
        if (FieldType.isInteger(fieldType)) {
            BigInteger integer = new BigInteger(String.valueOf(operand));
            BigInteger compareInteger = new BigInteger(String.valueOf(comparedValue));
            return !integer.equals(compareInteger);
        }
        if (FieldType.isDecimal(fieldType)) {
            BigDecimal bigDecimal = new BigDecimal(String.valueOf(operand));
            BigDecimal compareBigDecimal = new BigDecimal(String.valueOf(comparedValue));
            return !bigDecimal.equals(compareBigDecimal);
        }
        if (FieldType.isDate(fieldType)) {

        }
        Boolean booleanValue = (Boolean) operand;
        Boolean compareBoolean = (Boolean) comparedValue;
        return !booleanValue.equals(compareBoolean);
    };

    /**
     * Equal ignore case operation evaluator
     * */
    public static OperationEvaluator equalIgnoreCase = (operand, arguments) -> {
        checkSupportedType(FieldType.ofValue(operand), Operation.EQUAL_IGNORE_CASE);
        String string = (String) operand;
        String compareString = (String) arguments.get(0);
        return string.equalsIgnoreCase(compareString);
    };

    /**
     * Not equal ignore case operation evaluator
     * */
    public static OperationEvaluator notEqualIgnoreCase = (operand, arguments) -> {
        checkSupportedType(FieldType.ofValue(operand), Operation.EQUAL_IGNORE_CASE);
        String string = (String) operand;
        String compareString = (String) arguments.get(0);
        return !string.equalsIgnoreCase(compareString);
    };

    /**
     * Greater operation executor
     * */
    public static OperationEvaluator greater = (operand, arguments) -> {
        checkSupportedType(FieldType.ofValue(operand), Operation.GREATER);
        FieldType fieldType = FieldType.ofValue(operand);
        Object comparedValue = arguments.get(0);
        if (fieldType == FieldType.INTEGER) {
            BigInteger integer = new BigInteger(String.valueOf(operand));
            BigInteger compareInteger = new BigInteger(String.valueOf(comparedValue));
            return integer.compareTo(compareInteger) > 0;
        }
        BigDecimal decimal = new BigDecimal(String.valueOf(operand));
        BigDecimal compareDecimal = new BigDecimal(String.valueOf(comparedValue));
        return decimal.compareTo(compareDecimal) > 0;
    };

    /**
     * Greater than or equal to operation executor
     * */
    public static OperationEvaluator greaterEqual = (operand, arguments) -> {
        checkSupportedType(FieldType.ofValue(operand), Operation.GREATER_EQUAL);
        FieldType fieldType = FieldType.ofValue(operand);
        Object comparedValue = arguments.get(0);
        if (fieldType == FieldType.INTEGER) {
            BigInteger integer = new BigInteger(String.valueOf(operand));
            BigInteger compareInteger = new BigInteger(String.valueOf(comparedValue));
            return integer.compareTo(compareInteger) >= 0;
        }
        BigDecimal decimal = new BigDecimal(String.valueOf(operand));
        BigDecimal compareDecimal = new BigDecimal(String.valueOf(comparedValue));
        return decimal.compareTo(compareDecimal) >= 0;
    };

    /**
     * Lesser operation executor
     * */
    public static OperationEvaluator lesser = (operand, arguments) -> {
        checkSupportedType(FieldType.ofValue(operand), Operation.LESSER);
        FieldType fieldType = FieldType.ofValue(operand);
        Object comparedValue = arguments.get(0);
        if (fieldType == FieldType.INTEGER) {
            BigInteger integer = new BigInteger(String.valueOf(operand));
            BigInteger compareInteger = new BigInteger(String.valueOf(comparedValue));
            return integer.compareTo(compareInteger) < 0;
        }
        BigDecimal decimal = new BigDecimal(String.valueOf(operand));
        BigDecimal compareDecimal = new BigDecimal(String.valueOf(comparedValue));
        return decimal.compareTo(compareDecimal) < 0;
    };

    /**
     * Lesser than or equal to operation executor
     * */
    public static OperationEvaluator lesserEqual = (operand, arguments) -> {
        checkSupportedType(FieldType.ofValue(operand), Operation.LESSER_EQUAL);
        FieldType fieldType = FieldType.ofValue(operand);
        Object comparedValue = arguments.get(0);
        if (fieldType == FieldType.INTEGER) {
            BigInteger integer = new BigInteger(String.valueOf(operand));
            BigInteger compareInteger = new BigInteger(String.valueOf(comparedValue));
            return integer.compareTo(compareInteger) <= 0;
        }
        BigDecimal decimal = new BigDecimal(String.valueOf(operand));
        BigDecimal compareDecimal = new BigDecimal(String.valueOf(comparedValue));
        return decimal.compareTo(compareDecimal) <= 0;
    };

    /**
     * Between operation executor
     * */
    public static OperationEvaluator between = (operand, arguments) -> {
        checkSupportedType(FieldType.ofValue(operand), Operation.BETWEEN);
        Object comparedValueLeft = arguments.get(0);
        Object comparedValueRight = arguments.get(1);
        FieldType fieldType = FieldType.ofValue(operand);
        if (FieldType.isInteger(fieldType)) {
            BigInteger integer = new BigInteger(String.valueOf(operand));
            BigInteger compareIntegerLeft = new BigInteger(String.valueOf(comparedValueLeft));
            BigInteger compareIntegerRight = new BigInteger(String.valueOf(comparedValueRight));
            return integer.compareTo(compareIntegerLeft) >= 0 &&
                   integer.compareTo(compareIntegerRight) <= 0;
        }
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(operand));
        BigDecimal compareBigDecimalLeft = new BigDecimal(String.valueOf(comparedValueLeft));
        BigDecimal compareBigDecimalRight = new BigDecimal(String.valueOf(comparedValueRight));
        return bigDecimal.compareTo(compareBigDecimalLeft) >= 0 &&
               bigDecimal.compareTo(compareBigDecimalRight) <= 0;
    };

    /**
     * Start with operation executor
     * */
    public static OperationEvaluator startWith = (operand, arguments) -> {
        checkSupportedType(FieldType.ofValue(operand), Operation.STARTS_WITH);
        String comparedValue = (String) arguments.get(0);
        String value = (String) operand;
        return value.startsWith(comparedValue);
    };

    /**
     * End with operation executor
     * */
    public static OperationEvaluator endWith = (operand, arguments) -> {
        checkSupportedType(FieldType.ofValue(operand), Operation.ENDS_WITH);
        String comparedValue = (String) arguments.get(0);
        String value = (String) operand;
        return value.endsWith(comparedValue);
    };

    /**
     * Contains operation executor
     * */
    public static OperationEvaluator contains = (operand, arguments) -> {
        checkSupportedType(FieldType.ofValue(operand), Operation.CONTAINS);
        String comparedValue = (String) arguments.get(0);
        String value = (String) operand;
        return value.contains(comparedValue);
    };

    /**
     * Pattern operation executor
     * */
    public static OperationEvaluator pattern = (operand, arguments) -> {
        checkSupportedType(FieldType.ofValue(operand), Operation.PATTERN);
        String pattern = (String) arguments.get(0);
        String value = (String) operand;
        return value.matches(pattern);
    };

    /**
     * Length operation executor
     * */
    public static OperationEvaluator length = (operand, arguments) -> {
        checkSupportedType(FieldType.ofValue(operand), Operation.LENGTH);
        Integer compareSize = (Integer) arguments.get(0);
        FieldType fieldType = FieldType.ofValue(operand);
        if (fieldType == FieldType.COLLECTION) {
            if (operand.getClass().isArray()) {
                return Array.getLength(operand) == compareSize;
            } else {
                return ((Collection<?>) operand).size() == compareSize;
            }
        }
        String string = (String) operand;
        return string.length() == compareSize;
    };

    /**
     * Max length operation executor
     * */
    public static OperationEvaluator maxLength = (operand, arguments) -> {
        checkSupportedType(FieldType.ofValue(operand), Operation.MAX_LENGTH);
        Integer compareSize = (Integer) arguments.get(0);
        FieldType fieldType = FieldType.ofValue(operand);
        if (fieldType == FieldType.COLLECTION) {
            if (operand.getClass().isArray()) {
                return Array.getLength(operand) <= compareSize;
            } else {
                return ((Collection<?>) operand).size() <= compareSize;
            }
        }
        String string = (String) operand;
        return string.length() <= compareSize;
    };

    /**
     * Min length operation executor
     * */
    public static OperationEvaluator minLength = (operand, arguments) -> {
        checkSupportedType(FieldType.ofValue(operand), Operation.MIN_LENGTH);
        Integer compareSize = (Integer) arguments.get(0);
        FieldType fieldType = FieldType.ofValue(operand);
        if (fieldType == FieldType.COLLECTION) {
            if (operand.getClass().isArray()) {
                return Array.getLength(operand) >= compareSize;
            } else {
                return ((Collection<?>) operand).size() >= compareSize;
            }
        }
        String string = (String) operand;
        return string.length() >= compareSize;
    };

    /**
     * Utility method for {@link OperationEvaluator#execute(Object, List)}
     * */
    private static void checkSupportedType(FieldType fieldType, Operation operation) {
        if (!List.of(operation.getAppliedTypes()).contains(fieldType)) {
            throw new ValidationException("Unsupported operation: " + operation.getName() + " for field type: " + fieldType);
        }
    }
}
