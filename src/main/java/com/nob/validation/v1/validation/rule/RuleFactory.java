package com.nob.validation.v1.validation.rule;

import com.nob.validation.v1.configuration.ConfigurationConverter;
import com.nob.validation.v1.configuration.RuleConfiguration;
import com.nob.validation.v1.validation.policy.ObjectPolicy;
import com.nob.validation.v1.enumerate.FieldType;
import com.nob.validation.v1.exception.ValidationException;

import java.util.Objects;

public class RuleFactory {

    public static Rule getRule(RuleConfiguration configuration, FieldType fieldType) {
        if (Objects.nonNull(configuration.getSimpleType()))
            return selfElevationRuleFactory(configuration.getSimpleType(), configuration.getCondition());
        if (Objects.nonNull(configuration.getExpression()))
            return new ExpressionRule(
                    configuration.getExpression(),
                    configuration.getCondition(),
                    configuration.getMessage());
        if (Objects.nonNull(configuration.getOperation()))
            return new LiteralParameterRule(
                    configuration.getCondition(),
                    fieldType,
                    configuration.getOperation(),
                    configuration.getComparedValues(),
                    configuration.getMessage());
        if (Objects.nonNull(configuration.getInnerConfig())) {
            ObjectPolicy innerConfig = ConfigurationConverter.toObjectPolicy(configuration.getInnerConfig());
            if (FieldType.isCollection(fieldType))
                return new CollectionValueRule(configuration.getCondition(), innerConfig);
            else
                return new ObjectValueRule(configuration.getCondition(), innerConfig);
        }
        throw new ValidationException("Invalid configuration");
    }

    public static Rule selfElevationRuleFactory(String ruleName, String condition) {
        return switch (ruleName) {
            case "required" -> new Required(condition);
            case "not_empty" -> new NotEmpty(condition);
            default -> throw new IllegalArgumentException("Invalid self elevating rule name: " + ruleName);
        };
    }
}
