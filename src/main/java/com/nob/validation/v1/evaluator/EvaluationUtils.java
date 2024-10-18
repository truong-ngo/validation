package com.nob.validation.v1.evaluator;

import com.nob.validation.v1.validation.policy.FieldPolicy;
import com.nob.validation.v1.validation.rule.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EvaluationUtils {

    public static List<EvaluationResult> getEvaluationResults(Object item, List<FieldPolicy> rules) {
        List<EvaluationResult> objectResults = new ArrayList<>();
        for (FieldPolicy rule : rules) {
            List<Rule> fieldRules = rule.getRules();
            List<EvaluationResult> fieldResult = new ArrayList<>();
            for (Rule r : fieldRules) {
                EvaluationResult result = r.evaluate(item, rule.getFieldName());
                fieldResult.add(result);
            }
            if (fieldResult.isEmpty() || fieldResult.stream().allMatch(EvaluationResult::isValid)) {
                objectResults.add(EvaluationResult.valid());
            } else {
                List<Object> messages = fieldResult.stream()
                        .filter(r -> !r.isValid())
                        .map(EvaluationResult::getErrorMessage)
                        .toList();
                Map<String, Object> details = Map.of(rule.getFieldName(), messages);
                EvaluationResult evaluationResult = EvaluationResult.invalid(details);
                objectResults.add(evaluationResult);
            }
        }
        return objectResults;
    }

    public static Map<String, Object> getErrorMessageDetails(List<EvaluationResult> objectResults) {
        @SuppressWarnings("unchecked")
        Map<String, Object> details = objectResults.stream()
                .filter(r -> !r.isValid())
                .map(EvaluationResult::getErrorMessage)
                .map(e -> (Map<String, Object>) e)
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e, r) -> r));
        return details;
    }
}
