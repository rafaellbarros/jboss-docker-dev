package com.github.rafaellbarros.exception.mapper;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {

    private int status;
    private String error;
    private String message;
    private String path;
    private String method;
    private LocalDateTime timestamp;

    public ErrorResponse(int status, String error, String message, String path, String method) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.method = method;
        this.timestamp = LocalDateTime.now();
    }
}