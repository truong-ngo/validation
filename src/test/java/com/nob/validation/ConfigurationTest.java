package com.nob.validation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nob.validation.v1.configuration.ConfigurationConverter;
import com.nob.validation.v1.configuration.ConfigurationProvider;
import com.nob.validation.v1.configuration.ObjectConfiguration;
import com.nob.validation.v1.validation.policy.ObjectPolicy;
import com.nob.validation.v1.evaluator.EvaluationResult;
import com.nob.validation.v1.evaluator.ValidationEvaluator;
import lombok.Builder;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class ConfigurationTest {

    public static ConfigurationProvider provider = name -> {
        String path = "policy.json";
        return getResource(path, new TypeReference<ObjectConfiguration>(){}).orElse(null);
    };

    public static <T> Optional<T> getResource(String path, TypeReference<T> type) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
            InputStream in = resource.getInputStream();
            T result = new ObjectMapper().readValue(in.readAllBytes(), type);
            in.close();
            return Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static void main(String[] args) {

        ValidationTest.Person person = ValidationTest.Person.builder()
                .name("uni")
                .address(ValidationTest.Address.builder()
                        .street("")
                        .build())
                .build();

        ObjectConfiguration configuration = provider.getConfiguration("");
        ObjectPolicy policy = ConfigurationConverter.toObjectPolicy(configuration);
        ValidationEvaluator evaluator = new ValidationEvaluator();
        Instant start = Instant.now();
        EvaluationResult result = evaluator.evaluate(policy, person);
        Instant end = Instant.now();
        System.out.println(result.isValid());
        System.out.println(result.getErrorMessage());
        System.out.println(Duration.between(start, end).toMillis());
    }

    @Data
    @Builder
    public static class Person {
        private String name;
        private int age;
        private double salary;
        private String gender;
        private ValidationTest.Address address;
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
