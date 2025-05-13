package com.github.rafaellbarros.resource;

import com.github.rafaellbarros.model.Produto;
import com.github.rafaellbarros.response.StandardResponses;
import com.github.rafaellbarros.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;


@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Produtos", description = "Operações relacionadas a produtos")
@StandardResponses
public class ProdutoResource {

    @Inject
    private ProdutoService service;


    @GET
    @Operation(summary = "Listar todos os produtos",
            description = "Retorna uma lista com todos os produtos cadastrados")
    @ApiResponse(responseCode = "200",
            description = "Lista de produtos retornada com sucesso",
            content = @Content(schema = @Schema(implementation = Produto.class)))
    public List<Produto> listarTodos() {
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar produto por ID",
            description = "Retorna um produto específico baseado em seu ID")
    @ApiResponse(responseCode = "200",
            description = "Produto encontrado",
            content = @Content(schema = @Schema(implementation = Produto.class)))
    public Response buscarPorId(
            @Parameter(description = "ID do produto a ser buscado", required = true)
            @PathParam("id") Long id) {
        var produto = service.buscarPorId(id);
        return Response.ok(produto).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Criar novo produto",
            description = "Cadastra um novo produto no sistema"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Produto criado com sucesso",
            content = @Content(schema = @Schema(implementation = Produto.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados do produto inválidos"
    )
    public Response criar(
            @RequestBody(
                    description = "Dados do produto a ser criado",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Produto.class))
            )
            Produto produto
    ) {
        var produtoSalvo = service.salvar(produto);
        return Response.created(URI.create("/produtos/" + produtoSalvo.getId()))
                .entity(produtoSalvo)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Atualizar produto",
            description = "Atualiza os dados de um produto existente")
    @ApiResponse(responseCode = "200",
            description = "Produto atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = Produto.class)))
    @ApiResponse(responseCode = "400",
            description = "Dados do produto inválidos")
    public Response atualizar(
            @Parameter(description = "ID do produto a ser atualizado", required = true)
            @PathParam("id") Long id,
            @RequestBody(
                    description = "Dados atualizados do produto",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Produto.class))
            )
            Produto produto) {

        produto.setId(id);
        var produtoAtualizado = service.salvar(produto);
        return Response.ok(produtoAtualizado).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Remover produto",
            description = "Remove um produto do sistema")
    @ApiResponse(responseCode = "204",
            description = "Produto removido com sucesso")
    public Response remover(
            @Parameter(description = "ID do produto a ser removido", required = true)
            @PathParam("id") Long id) {
        service.remover(id);
        return Response.noContent().build();
    }
}