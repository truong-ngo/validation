package com.nob.validation.v1.validation.policy;

import com.nob.validation.v1.validation.rule.Rule;
import com.nob.validation.v1.enumerate.FieldType;
import com.nob.validation.v1.evaluator.EvaluationResult;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Rules / Policy apply on one field
 * */
@Data
public class FieldPolicy {
    private String fieldName;
    private FieldType fieldType;
    private List<Rule> rules;

    public EvaluationResult evaluate(Object context) {
        List<EvaluationResult> results = rules.stream().map(r -> r.evaluate(context, fieldName)).toList();
        boolean isValid = results.stream().allMatch(EvaluationResult::isValid);
        if (isValid) return EvaluationResult.valid();
        List<Object> message = results.stream()
                .filter(r -> !r.isValid())
                .map(EvaluationResult::getErrorMessage)
                .toList();
        return EvaluationResult.invalid(Map.of(fieldName, message));
    }
}
