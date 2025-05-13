package com.github.rafaellbarros.exception.mapper;


import com.github.rafaellbarros.exception.BusinessNotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Collections;


@Provider
public class BusinessNotExceptionMapper implements ExceptionMapper<BusinessNotException> {
    private static final Logger LOG = LoggerFactory.getLogger(BusinessNotExceptionMapper.class);

    @Context
    private UriInfo uriInfo;

    @Context
    private Request request;

    @Override
    public Response toResponse(BusinessNotException exception) {
        String requestId = MDC.get("requestId");
        String path = uriInfo.getPath();

        LOG.info("Resource Not Found - RequestId: {}, Path: {}, Method: {}, Details: {}",
                requestId, path, request.getMethod(), exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder(
                        Response.Status.NOT_FOUND.getStatusCode(),
                        "Not Found",
                        exception.getMessage())  // Usando a mensagem da exceção
                .path(path)
                .method(request.getMethod())
                .requestId(requestId)
                // Removendo os details ou usando um valor padrão
                .details(Collections.singletonMap("resource", "Not found"))
                .build();

        return Response.status(Response.Status.NOT_FOUND)
                .entity(errorResponse)
                .type("application/json")
                .build();
    }
}