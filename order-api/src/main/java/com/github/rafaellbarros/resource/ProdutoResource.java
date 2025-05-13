package com.github.rafaellbarros.resource;

import com.github.rafaellbarros.dto.ProdutoRequestDTO;
import com.github.rafaellbarros.dto.ProdutoResponseDTO;
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
import javax.validation.Valid;
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
            content = @Content(schema = @Schema(implementation = ProdutoResponseDTO.class)))
    public List<ProdutoResponseDTO> listarTodos() {
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar produto por ID",
            description = "Retorna um produto específico baseado em seu ID")
    @ApiResponse(responseCode = "200",
            description = "Produto encontrado",
            content = @Content(schema = @Schema(implementation = ProdutoResponseDTO.class)))
    public Response buscarPorId(
            @Parameter(description = "ID do produto a ser buscado", required = true)
            @PathParam("id") Long id) {
        var produto = service.buscarPorId(id);
        return Response.ok(produto).build();
    }

    @POST
    @Operation(summary = "Criar novo produto")
    @ApiResponse(responseCode = "201",
            description = "Produto criado com sucesso",
            content = @Content(schema = @Schema(implementation = ProdutoResponseDTO.class)))
    @ApiResponse(responseCode = "400",
            description = "Dados inválidos")
    public Response criar(
            @RequestBody(
                    description = "Dados do produto",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ProdutoRequestDTO.class)))
            @Valid ProdutoRequestDTO produtoDTO) {

        var produtoSalvo = service.salvar(produtoDTO);
        return Response.created(URI.create("/produtos/" + produtoSalvo.getId()))
                .entity(produtoSalvo)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar produto")
    @ApiResponse(responseCode = "200",
            description = "Produto atualizado",
            content = @Content(schema = @Schema(implementation = ProdutoResponseDTO.class)))
    @ApiResponse(responseCode = "400",
            description = "Dados inválidos")
    @ApiResponse(responseCode = "404",
            description = "Produto não encontrado")
    public Response atualizar(
            @Parameter(description = "ID do produto", required = true)
            @PathParam("id") Long id,

            @RequestBody(
                    description = "Dados atualizados do produto",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ProdutoRequestDTO.class)))
            @Valid ProdutoRequestDTO produtoDTO) {

        var produtoAtualizado = service.atualizar(id, produtoDTO);
        return Response.ok(produtoAtualizado).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Remover produto")
    @ApiResponse(responseCode = "204",
            description = "Produto removido")
    @ApiResponse(responseCode = "404",
            description = "Produto não encontrado")
    public Response remover(
            @Parameter(description = "ID do produto", required = true)
            @PathParam("id") Long id) {

        service.remover(id);
        return Response.noContent().build();
    }
}