package com.nob.validation;

import com.nob.validation.v1.validation.policy.*;
import com.nob.validation.v1.validation.rule.*;
import com.nob.validation.v1.enumerate.FieldType;
import com.nob.validation.v1.enumerate.Operation;
import com.nob.validation.v1.evaluator.EvaluationResult;
import com.nob.validation.v1.evaluator.ValidationEvaluator;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationTest {

    public static void main(String[] args) {
        FieldPolicy namePolicy = getNameRule();
        ObjectPolicy config = new ObjectPolicy();
        config.setObjectName("person");


        FieldPolicy addressPolicy = new FieldPolicy();
        Rule addressRule = new ObjectValueRule(null, getAddressPolicy());
        addressPolicy.setFieldName("address");
        addressPolicy.setFieldType(FieldType.OBJECT);
        addressPolicy.setRules(List.of(addressRule));

        config.setFieldPolicies(List.of(namePolicy, addressPolicy));

        Person person = Person.builder()
                .name("uni")
                .address(Address.builder()
                        .street("")
                        .build())
                .build();
        ValidationEvaluator executor = new ValidationEvaluator();

        List<Long> times = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Instant start = Instant.now();
            EvaluationResult result = executor.evaluate(config, person);
            Instant end = Instant.now();
            times.add(Duration.between(start, end).toMillis());
        }

//        Instant start = Instant.now();
//        EvaluationResult result = executor.evaluate(config, person);
//        Instant end = Instant.now();
//        times.add(Duration.between(start, end).toMillis());

        System.out.println("Duration: " + times.stream().mapToLong(Long::longValue).average().getAsDouble());
    }

    private static FieldPolicy getNameRule() {
        Rule required = new Required(null);
        Rule nameNotEmpty = new NotEmpty("name != null");
        Rule nameMinSize = new LiteralParameterRule(
                "name != null",
                FieldType.STRING,
                Operation.MIN_LENGTH,
                List.of(5),
                "Name must be at least 5 character"
        );

        Rule containLetter = new LiteralParameterRule(
                "name != null",
                FieldType.STRING,
                Operation.CONTAINS,
                List.of("a"),
                "Name must be contains 'a' letter"
        );

        FieldPolicy fieldPolicy = new FieldPolicy();
        fieldPolicy.setFieldName("name");
        fieldPolicy.setFieldType(FieldType.STRING);
        fieldPolicy.setRules(List.of(required, nameMinSize, containLetter));
        return fieldPolicy;
    }

    private static FieldPolicy getExpressionRule() {
        Rule required = new Required(null);
        Rule minSize = new ExpressionRule("name != null", "name.length() >= 5", "Must be at least 5 characters");
        Rule contain = new ExpressionRule("name != null", "name.contains('a')", "Must be contains 'a' letter");
        FieldPolicy fieldPolicy = new FieldPolicy();
        fieldPolicy.setFieldName("name");
        fieldPolicy.setFieldType(FieldType.STRING);
        fieldPolicy.setRules(List.of(required, minSize, contain));
        return fieldPolicy;
    }

    private static ObjectPolicy getAddressPolicy() {
        Rule required = new Required(null);
        Rule streetRule = new LiteralParameterRule(
                "street != null",
                FieldType.STRING,
                Operation.MIN_LENGTH,
                List.of(1),
                "Street must be at least 1 character"
        );

        FieldPolicy street = new FieldPolicy();
        street.setFieldName("street");
        street.setFieldType(FieldType.STRING);
        street.setRules(List.of(required, streetRule));

        FieldPolicy city = new FieldPolicy();
        city.setFieldName("city");
        city.setFieldType(FieldType.STRING);
        city.setRules(List.of(required));

        return new ObjectPolicy("address", List.of(street, city));
    }

    public static void benchmark() {

    }

    @Data
    @Builder
    public static class Person {
        private String name;
        private int age;
        private double salary;
        private String gender;
        private Address address;
    }

    @Data
    @Builder
    public static class Address {
        private String street;
        private String ward;
        private String province;
        private String city;
    }
}
