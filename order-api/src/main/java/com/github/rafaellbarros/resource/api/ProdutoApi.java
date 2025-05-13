package com.github.rafaellbarros.resource.api;

import com.github.rafaellbarros.dto.ProdutoRequestDTO;
import com.github.rafaellbarros.dto.ProdutoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

import static com.github.rafaellbarros.resource.api.ApiConstants.*;


@ApiStandardResponses
@Tag(name = "Produtos", description = "Operações para gerenciamento de produtos")
public interface ProdutoApi {

    @GET
    @Path("/paginacao")
    @Operation(summary = "Listar produtos com paginação",
            description = "Retorna uma lista paginada de produtos com possibilidade de ordenação")
    @ApiResponse(responseCode = OK_CODE,
            description = LIST_SUCCESS,
            content = @Content(schema = @Schema(implementation = ProdutoResponseDTO.class)))
    Response listarPaginado(
            @Parameter(description = "Número da página (base 0)", example = "0")
            @QueryParam("page") @DefaultValue("0") int page,

            @Parameter(description = "Quantidade de itens por página", example = "10")
            @QueryParam("size") @DefaultValue("10") int size,

            @Parameter(description = "Campo para ordenação (nome, preco, etc.)", example = "nome")
            @QueryParam("sort") String ordination,

            @Parameter(description = "Direção da ordenação (ASC ou DESC)", example = "ASC")
            @QueryParam("direction") @DefaultValue("ASC") String direction);

    @GET
    @Path("/pesquisar")
    @Operation(summary = "Pesquisar produtos com filtros",
            description = "Retorna produtos filtrados com paginação")
    @ApiResponse(responseCode = OK_CODE,
            description = SEARCH_SUCCESS,
            content = @Content(schema = @Schema(implementation = ProdutoResponseDTO.class)))
    Response pesquisar(
            @Parameter(description = "Filtro por nome (busca parcial)", example = "notebook")
            @QueryParam("nome") String nome,

            @Parameter(description = "Filtro por descrição (busca parcial)", example = "gamer")
            @QueryParam("descricao") String descricao,

            @Parameter(description = "Preço mínimo do produto", example = "1000")
            @QueryParam("precoMin") BigDecimal precoMin,

            @Parameter(description = "Preço máximo do produto", example = "5000")
            @QueryParam("precoMax") BigDecimal precoMax,

            @Parameter(description = "Número da página (base 0)", example = "0")
            @QueryParam("page") @DefaultValue("0") int page,

            @Parameter(description = "Quantidade de itens por página", example = "10")
            @QueryParam("size") @DefaultValue("10") int size,

            @Parameter(description = "Campo para ordenação (nome, preco, etc.)", example = "preco")
            @QueryParam("sort") String ordination,

            @Parameter(description = "Direção da ordenação (ASC ou DESC)", example = "ASC")
            @QueryParam("direction") @DefaultValue("ASC") String direction);

    @GET
    @Operation(summary = "Listar todos os produtos",
            description = "Retorna todos os produtos cadastrados no sistema")
    @ApiResponse(responseCode = OK_CODE,
            description = LIST_SUCCESS,
            content = @Content(schema = @Schema(implementation = ProdutoResponseDTO.class)))
    Response listarTodos();

    @GET
    @Path("/{id}")
    @Operation(summary = "Obter detalhes de um produto",
            description = "Retorna os detalhes completos de um produto específico")
    @ApiResponse(responseCode = OK_CODE,
            description = RESOURCE_FOUND,
            content = @Content(schema = @Schema(implementation = ProdutoResponseDTO.class)))
    @ApiResponse(responseCode = NOT_FOUND_CODE,
            description = NOT_FOUND_DESCRIPTION)
    Response buscarPorId(
            @Parameter(description = "ID único do produto", required = true, example = "1")
            @PathParam("id") Long id);

    @POST
    @Operation(summary = "Criar um novo produto",
            description = "Cadastra um novo produto no sistema")
    @ApiResponse(responseCode = CREATED_CODE,
            description = CREATED_DESCRIPTION,
            content = @Content(schema = @Schema(implementation = ProdutoResponseDTO.class)))
    @ApiResponse(responseCode = BAD_REQUEST_CODE,
            description = VALIDATION_ERROR)
    Response criar(
            @RequestBody(
                    description = "Dados do novo produto",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ProdutoRequestDTO.class)))
            @Valid ProdutoRequestDTO produtoDTO);

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar um produto existente",
            description = "Atualiza todos os dados de um produto específico")
    @ApiResponse(responseCode = OK_CODE,
            description = RESOURCE_UPDATED,
            content = @Content(schema = @Schema(implementation = ProdutoResponseDTO.class)))
    @ApiResponse(responseCode = NOT_FOUND_CODE,
            description = NOT_FOUND_DESCRIPTION)
    @ApiResponse(responseCode = BAD_REQUEST_CODE,
            description = VALIDATION_ERROR)
    Response atualizar(
            @Parameter(description = "ID único do produto", required = true, example = "1")
            @PathParam("id") Long id,
            @RequestBody(
                    description = "Dados atualizados do produto",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ProdutoRequestDTO.class)))
            @Valid ProdutoRequestDTO produtoDTO);

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Remover um produto",
            description = "Remove permanentemente um produto do sistema")
    @ApiResponse(responseCode = NO_CONTENT_CODE,
            description = RESOURCE_DELETED)
    @ApiResponse(responseCode = NOT_FOUND_CODE,
            description = NOT_FOUND_DESCRIPTION)
    Response remover(
            @Parameter(description = "ID único do produto", required = true, example = "1")
            @PathParam("id") Long id);
}