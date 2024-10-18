package com.nob.validation.v1.validation.rule;

import com.nob.validation.v1.exception.Message;
import com.nob.validation.v1.exception.ValidationException;
import com.nob.validation.v1.evaluator.EvaluationResult;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Objects;

/**
 * Represent not empty rule of object's field
 * */
public class NotEmpty extends AbstractRule implements SelfEvaluatingRule {

    public NotEmpty(String condition) {
        super(condition);
    }

    @Override
    public String getErrorMessage() {
        return Message.NOT_EMPTY;
    }

    @Override
    public EvaluationResult evaluate(Object context, String fieldName) {
        Boolean condition = isApplicable(context);
        if (Objects.isNull(condition)) return EvaluationResult.invalid(Message.invalidExpression(getCondition()));
        if (!condition) return EvaluationResult.valid();
        Object value = getFieldValue(fieldName, context);
        if (Objects.isNull(value)) return EvaluationResult.valid();
        if (value instanceof String str) {
            return str.trim().isEmpty() ?
                    EvaluationResult.invalid(Message.NOT_EMPTY) :
                    EvaluationResult.valid();
        }
        if (value instanceof Collection<?> col) {
            return col.isEmpty() ?
                    EvaluationResult.invalid(Message.NOT_EMPTY) :
                    EvaluationResult.valid();
        }
        if (value.getClass().isArray()) {
            return Array.getLength(value) == 0 ?
                    EvaluationResult.invalid(Message.NOT_EMPTY) :
                    EvaluationResult.valid();
        }
        throw new ValidationException(Message.NOT_APPLICABLE);
    }
}
