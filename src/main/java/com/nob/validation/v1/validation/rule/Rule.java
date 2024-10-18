package com.nob.validation.v1.validation.rule;

import com.nob.validation.v1.evaluator.EvaluationResult;

/**
 * An interface represent the validation rule apply for object property
 * */
public interface Rule {

    /**
     * Applicable condition
     * @param context the context to resolve condition
     * @return null if condition can not be resolved
     * */
    Boolean isApplicable(Object context);

    /**
     * Get the value of field that need to be validated
     * @param fieldName the name of field
     * @param context the context of validation
     * @return the field's value
     * */
    Object getFieldValue(String fieldName, Object context);

    /**
     * Error message while evaluating rule
     * */
    Object getErrorMessage();

    /**
     * Evaluation of rule
     * @param context object that need to be validated
     * @return {@link EvaluationResult} result of evaluation process
     * */
    EvaluationResult evaluate(Object context, String fieldName);
}
