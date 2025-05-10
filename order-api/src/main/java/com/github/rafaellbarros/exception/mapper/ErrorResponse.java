package com.github.rafaellbarros.exception.mapper;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private int status;
    private String error;
    private String message;
    private String path;
    private String method;


    public ErrorResponse(int status, String error, String message, String path, String method) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.method = method;
    }
}