package com.github.rafaellbarros.resource;

import com.github.rafaellbarros.model.Produto;
import com.github.rafaellbarros.service.ProdutoService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Produtos", description = "Operações para gerenciamento de produtos")
public class ProdutoResource {
    
    @Inject
    private ProdutoService service;

    @GET
    @Operation(
            summary = "Listar todos os produtos",
            description = "Retorna uma lista paginada de todos os produtos disponíveis"
    )
    @APIResponse(
            responseCode = "200",
            description = "Lista de produtos retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Produto.class, type = SchemaType.ARRAY)))
    public List<Produto> listarTodos() {
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Buscar produto por ID",
            description = "Retorna um produto específico baseado no ID"
    )
    @APIResponse(
            responseCode = "200",
            description = "Produto encontrado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Produto.class)))
    @APIResponse(
            responseCode = "404",
            description = "Produto não encontrado")
    public Response buscarPorId(@PathParam("id") Long id) {
        Produto produto = service.buscarPorId(id);
        return Response.ok(produto).build();
    }


    @POST
    @Operation(
            summary = "Criar novo produto",
            description = "Cadastra um novo produto no sistema"
    )
    @APIResponse(
            responseCode = "201",
            description = "Produto criado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Produto.class)))
    @APIResponse(
            responseCode = "400",
            description = "Dados inválidos fornecidos")
    public Response criar(
            @Parameter(
                    description = "Dados do produto a ser criado",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Produto.class))
            )
            Produto produto) {
        Produto produtoSalvo = service.salvar(produto);
        return Response.created(URI.create("/produtos/" + produtoSalvo.getId())).entity(produtoSalvo).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(
            summary = "Atualizar produto",
            description = "Atualiza os dados de um produto existente"
    )
    @APIResponse(
            responseCode = "200",
            description = "Produto atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Produto.class)))
    @APIResponse(
            responseCode = "404",
            description = "Produto não encontrado")
    @APIResponse(
            responseCode = "400",
            description = "Dados inválidos fornecidos")
    public Response atualizar(
            @Parameter(
                    description = "ID do produto a ser atualizado",
                    required = true,
                    example = "123"
            )
            @PathParam("id") Long id,
            @Parameter(
                    description = "Dados atualizados do produto",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Produto.class))
            )
            Produto produto) {
        produto.setId(id);
        Produto salvared = service.salvar(produto);
        return Response.ok(salvared).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(
            summary = "Remover produto",
            description = "Remove um produto do sistema"
    )
    @APIResponse(
            responseCode = "204",
            description = "Produto removido com sucesso")
    @APIResponse(
            responseCode = "404",
            description = "Produto não encontrado")
    public Response remover(
            @Parameter(
                    description = "ID do produto a ser removido",
                    required = true,
                    example = "123"
            )
            @PathParam("id") Long id) {
        service.remover(id);
        return Response.noContent().build();
    }
}