package com.nob.validation.v1.configuration;

import com.nob.validation.v1.validation.policy.FieldPolicy;
import com.nob.validation.v1.validation.policy.ObjectPolicy;
import com.nob.validation.v1.validation.rule.Rule;
import com.nob.validation.v1.validation.rule.RuleFactory;

import java.util.List;

public class ConfigurationConverter {

    public static ObjectPolicy toObjectPolicy(ObjectConfiguration config) {
        ObjectPolicy policy = new ObjectPolicy();
        policy.setObjectName(config.getObjectName());
        List<FieldPolicy> fieldPolicies = config.getFieldConfigurations()
                .stream()
                .map(ConfigurationConverter::toFieldPolicy)
                .toList();
        policy.setFieldPolicies(fieldPolicies);
        return policy;
    }

    public static FieldPolicy toFieldPolicy(FieldConfiguration config) {
        FieldPolicy policy = new FieldPolicy();
        policy.setFieldName(config.getFieldName());
        policy.setFieldType(config.getFieldType());
        List<Rule> rules = config.getRules()
                .stream()
                .map(c -> RuleFactory.getRule(c, config.getFieldType()))
                .toList();
        policy.setRules(rules);
        return policy;
    }
}
