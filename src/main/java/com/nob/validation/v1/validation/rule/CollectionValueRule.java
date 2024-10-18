package com.nob.validation.v1.validation.rule;

import com.nob.validation.v1.validation.policy.FieldPolicy;
import com.nob.validation.v1.validation.policy.ObjectPolicy;
import com.nob.validation.v1.exception.Message;
import com.nob.validation.v1.evaluator.EvaluationResult;
import com.nob.validation.v1.evaluator.EvaluationUtils;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

public class CollectionValueRule extends AbstractRule implements ObjectTypeValueRule {

    private final ObjectPolicy innerRule;

    @Setter
    private Object message;

    /**
     * For implementation usage
     *
     * @param condition
     */
    protected CollectionValueRule(String condition, ObjectPolicy innerRule) {
        super(condition);
        this.innerRule = innerRule;
    }

    @Override
    public ObjectPolicy getInnerRule() {
        return innerRule;
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
        List<?> collection;
        if (value.getClass().isArray()) {
            collection = List.of((Object[]) value);
        } else {
            Collection<?> col = (Collection<?>) value;
            collection = List.of(col.toArray());
        }
        List<FieldPolicy> rules = innerRule.getFieldPolicies();
        int i = 0;
        List<Map<String, Object>> finalResult = new ArrayList<>();
        for (Object item : collection) {
            List<EvaluationResult> objectResults = EvaluationUtils.getEvaluationResults(item, rules);
            boolean result = objectResults.stream().allMatch(EvaluationResult::isValid);
            if (result) continue;
            Map<String, Object> details = EvaluationUtils.getErrorMessageDetails(objectResults);
            Map<String, Object> message = Map.of("[" + i + "]", details);
            finalResult.add(message);
            i++;
        }
        if (finalResult.isEmpty()) return EvaluationResult.valid();
        Map<String, Object> message = finalResult.stream()
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e, r) -> r));
        return EvaluationResult.invalid(message);
    }
}
