package com.nob.validation.v1.evaluator;

import lombok.Getter;

/**
 * Indicate rule evaluation process result
 * */
public class EvaluationResult {

    /**
     * Valid instance of {@code EvaluationResult}
     * */
    private static final EvaluationResult VALID = new EvaluationResult(true, null);

    /**
     * Indicate that result is valid or not
     * */
    private final boolean result;

    /**
     * Error message during the evaluation process
     * <p>
     * Message can be either be {@code String} or {@code List<String>}
     * */
    @Getter
    private final Object errorMessage;

    /**
     * Prevent usage of this constructor, using factory method instead
     * @see #valid()
     * @see #invalid(Object)
     * */
    private EvaluationResult(boolean result, Object errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
    }

    /**
     * Factory method for valid {@code EvaluationResult}
     * */
    public static EvaluationResult valid() {
        return VALID;
    }

    /**
     * Factory method for invalid {@code EvaluationResult}
     * @param errorMessage message
     * */
    public static EvaluationResult invalid(Object errorMessage) {
        return new EvaluationResult(false, errorMessage);
    }

    /**
     * Check if result is valid or not
     * */
    public boolean isValid() {
        return this.result;
    }
}
