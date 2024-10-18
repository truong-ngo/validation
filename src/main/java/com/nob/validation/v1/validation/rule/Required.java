package com.nob.validation.v1.validation.rule;

import com.nob.validation.v1.exception.Message;
import com.nob.validation.v1.evaluator.EvaluationResult;

import java.util.Objects;

/**
 * Represent required rule on object's field
 * */
public class Required extends AbstractRule implements SelfEvaluatingRule {

    public Required(String condition) {
        super(condition);
    }

    @Override
    public EvaluationResult evaluate(Object context, String fieldName) {
        Boolean condition = isApplicable(context);
        if (Objects.isNull(condition)) return EvaluationResult.invalid(Message.invalidExpression(getCondition()));
        if (!condition) return EvaluationResult.valid();
        return (Objects.nonNull(getFieldValue(fieldName, context))) ?
                EvaluationResult.valid() :
                EvaluationResult.invalid(getErrorMessage());
    }

    @Override
    public String getErrorMessage() {
        return Message.REQUIRED;
    }
}
