package com.github.rafaellbarros.exception;

import java.util.Map;

public class BusinessNotException extends RuntimeException {

    private final Map<String, Object> resourceDetails;

    public BusinessNotException(String message) {
        this(message, null);
    }

    public BusinessNotException(String message, Map<String, Object> resourceDetails) {
        super(message);
        this.resourceDetails = resourceDetails;
    }

    public Map<String, Object> getResourceDetails() {
        return resourceDetails;
    }
}
