package com.nob.validation.v1.evaluator;

import java.util.List;

public interface OperationEvaluator {
    boolean execute(Object operand, List<Object> arguments);
}
