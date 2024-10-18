package com.nob.validation.v1.validation.rule;

import com.nob.validation.v1.exception.Message;
import com.nob.validation.v1.evaluator.EvaluationResult;
import com.nob.validation.v1.evaluator.OperationEvaluators;
import com.nob.validation.v1.enumerate.FieldType;
import com.nob.validation.v1.enumerate.Operation;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class LiteralParameterRule extends AbstractRule implements LiteralValueRule {

    private FieldType fieldType;
    private Operation operation;
    private List<Object> comparedValues;
    private String message;

    /**
     * For implementation usage
     *
     * @param condition
     */
    protected LiteralParameterRule(String condition) {
        super(condition);
    }

    public LiteralParameterRule(String condition, FieldType fieldType, Operation operation, List<Object> comparedValues, String message) {
        super(condition);
        this.fieldType = fieldType;
        this.operation = operation;
        this.comparedValues = comparedValues;
        this.message = message;
    }

    @Override
    public FieldType getFieldType() {
        return fieldType;
    }

    @Override
    public Object getErrorMessage() {
        return message;
    }

    @Override
    public EvaluationResult evaluate(Object context, String fieldName) {
        Boolean condition = isApplicable(context);
        if (Objects.isNull(condition)) return EvaluationResult.invalid(Message.invalidExpression(getCondition()));
        if (!condition) return EvaluationResult.valid();
        Object value = getFieldValue(fieldName, context);
        if (Objects.isNull(value)) return EvaluationResult.valid();
        boolean result = OperationEvaluators.execute(operation, value, comparedValues);
        return result ? EvaluationResult.valid() : EvaluationResult.invalid(message);
    }
}
