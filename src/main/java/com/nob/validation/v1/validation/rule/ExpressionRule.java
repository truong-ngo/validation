package com.nob.validation.v1.validation.rule;

import com.nob.validation.v1.exception.Message;
import com.nob.validation.v1.Utils;
import com.nob.validation.v1.evaluator.EvaluationResult;
import lombok.Getter;

import java.util.Objects;

@Getter
public class ExpressionRule extends AbstractRule implements Rule {

    private final String expression;
    private final String message;

    public ExpressionRule(String condition, String expression, String message) {
        super(condition);
        this.expression = expression;
        this.message = message;
    }

    @Override
    public String getErrorMessage() {
        return message;
    }

    @Override
    public EvaluationResult evaluate(Object context, String fieldName) {
        Boolean condition = isApplicable(context);
        if (Objects.isNull(condition) || !condition) {
            return EvaluationResult.valid();
        }
        Boolean result = Utils.resolveExpression(expression, context);
        return Objects.nonNull(result) ?
                result ? EvaluationResult.valid() : EvaluationResult.invalid(getErrorMessage()) :
                EvaluationResult.invalid(Message.invalidExpression(expression));
    }
}
