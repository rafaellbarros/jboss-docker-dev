package com.github.rafaellbarros.exception.mapper;


import com.github.rafaellbarros.exception.NotFoundException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Context  // Injeta informações da URI da requisição
    private UriInfo uriInfo;


    @Context  // Injeta informações da requisição (método HTTP)
    private Request request;

    @Override
    public Response toResponse(NotFoundException exception) {
        // Obtém o caminho da requisição que causou o erro
        String path = uriInfo.getPath();

        ErrorResponse error = new ErrorResponse(
                Response.Status.NOT_FOUND.getStatusCode(),
                "Recurso não encontrado",
                exception.getMessage(),
                request.getMethod(),
                path  // Adiciona o caminho ao response
        );

        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(error)
                .type("application/json")
                .build();
    }
}