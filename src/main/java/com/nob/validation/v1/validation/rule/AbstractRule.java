package com.nob.validation.v1.validation.rule;

import com.nob.validation.v1.Utils;
import com.nob.validation.v1.exception.ValidationException;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Wrap the common condition property of {@code Rule}
 * */
@Getter
public abstract class AbstractRule implements Rule {

    /**
     * Expression that represent the applicability of rule
     * */
    private final String condition;

    /**
     * For implementation usage
     * */
    protected AbstractRule(String condition) {
        this.condition = condition;
    }

    /**
     * Base implementation for {@link Rule#isApplicable(Object)} method
     * */
    @Override
    public Boolean isApplicable(Object context) {
        if (Objects.isNull(condition)) return true;
        return Utils.resolveExpression(condition, context);
    }

    /**
     * Base implementation for {@link Rule#getFieldValue(String, Object)} method
     * */
    @Override
    public Object getFieldValue(String fieldName, Object context) {
        Field field = Utils.propertyOf(fieldName, context);
        if (Objects.isNull(field)) throw new ValidationException("Field: " + fieldName + " not found!");
        field.setAccessible(true);
        try {
            return field.get(context);
        } catch (IllegalAccessException e) {
            throw new ValidationException("Unable to access field: " + fieldName + "!");
        }
    }
}
