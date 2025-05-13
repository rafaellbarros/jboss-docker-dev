package com.github.rafaellbarros.exception.mapper;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final Instant timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final String method;
    private final String requestId;
    private final Map<String, Object> details;

    public static ErrorResponseBuilder builder(int status, String error, String message) {
        return new ErrorResponseBuilder()
                .timestamp(Instant.now())
                .status(status)
                .error(error)
                .message(message);
    }
}