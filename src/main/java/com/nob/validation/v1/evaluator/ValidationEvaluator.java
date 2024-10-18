package com.nob.validation.v1.evaluator;

import com.nob.validation.v1.validation.policy.FieldPolicy;
import com.nob.validation.v1.validation.policy.ObjectPolicy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidationEvaluator {

    @SuppressWarnings("unchecked")
    public EvaluationResult evaluate(ObjectPolicy config, Object context) {
        List<FieldPolicy> rules = config.getFieldPolicies();
        List<EvaluationResult> results = rules.stream().map(r -> r.evaluate(context)).toList();
        boolean result = results.stream().allMatch(EvaluationResult::isValid);
        if (result) return EvaluationResult.valid();
        Map<String, Object> messages = results.stream()
                .filter(r -> !r.isValid())
                .map(r -> (Map<String, Object>) r.getErrorMessage())
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue));
        return EvaluationResult.invalid(messages);
    }
}
