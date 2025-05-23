package com.github.rafaellbarros.exception.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Collections;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {
    private static final Logger LOG = LoggerFactory.getLogger(BadRequestExceptionMapper.class);

    @Context
    private UriInfo uriInfo;

    @Context
    private Request request;

    @Override
    public Response toResponse(BadRequestException exception) {
        String requestId = MDC.get("requestId");
        String path = uriInfo.getPath();

        LOG.warn("Bad Request - RequestId: {}, Path: {}, Method: {}, Error: {}",
                requestId, path, request.getMethod(), exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder(
                        Response.Status.BAD_REQUEST.getStatusCode(),
                        "Bad Request",
                        "Invalid request data. Please check your input.")
                .path(path)
                .method(request.getMethod())
                .requestId(requestId)
                .details(exception.getMessage() != null ?
                        Collections.singletonMap("validation", exception.getMessage()) :
                        Collections.singletonMap("validation", "Input validation failed"))
                .build();

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .type("application/json")
                .build();
    }
}