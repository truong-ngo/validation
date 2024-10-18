package com.nob.validation.v1.validation.rule;

import com.nob.validation.v1.validation.policy.FieldPolicy;
import com.nob.validation.v1.validation.policy.ObjectPolicy;
import com.nob.validation.v1.exception.Message;
import com.nob.validation.v1.evaluator.EvaluationResult;
import com.nob.validation.v1.evaluator.EvaluationUtils;
import lombok.Setter;

import java.util.*;

public class ObjectValueRule extends AbstractRule implements ObjectTypeValueRule {

    private final ObjectPolicy innerRule;

    @Setter
    private Object message;

    /**
     * For implementation usage
     *
     * @param condition
     */
    public ObjectValueRule(String condition, ObjectPolicy innerRule) {
        super(condition);
        this.innerRule = innerRule;
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
        List<FieldPolicy> rules = innerRule.getFieldPolicies();
        List<EvaluationResult> objectResults = EvaluationUtils.getEvaluationResults(value, rules);
        boolean result = objectResults.stream().allMatch(EvaluationResult::isValid);
        if (result) return EvaluationResult.valid();
        Map<String, Object> details = EvaluationUtils.getErrorMessageDetails(objectResults);
        return EvaluationResult.invalid(details);
    }

    @Override
    public ObjectPolicy getInnerRule() {
        return innerRule;
    }
}
