package com.nob.validation.v1.configuration;

public interface ConfigurationProvider {
    ObjectConfiguration getConfiguration(String policyName);
}
