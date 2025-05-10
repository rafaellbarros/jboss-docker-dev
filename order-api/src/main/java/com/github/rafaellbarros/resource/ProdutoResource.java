package com.github.rafaellbarros.resource;

import com.github.rafaellbarros.model.Produto;
import com.github.rafaellbarros.service.ProdutoService;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @Inject
    private ProdutoService service;

    @GET
    public List<Produto> listarTodos() {
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Produto produto = service.buscarPorId(id);
        return Response.ok(produto).build();
    }


    @POST
    public Response criar(Produto produto) {
        Produto produtoSalvo = service.salvar(produto);
        return Response.created(URI.create("/produtos/" + produtoSalvo.getId())).entity(produtoSalvo).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, Produto produto) {
        produto.setId(id);
        Produto salvared = service.salvar(produto);
        return Response.ok(salvared).build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") Long id) {
        service.remover(id);
        return Response.noContent().build();
    }
}