package com.github.rafaellbarros.resource;

import com.github.rafaellbarros.dto.PageResposeDTO;
import com.github.rafaellbarros.dto.ProdutoFiltroDTO;
import com.github.rafaellbarros.dto.ProdutoRequestDTO;
import com.github.rafaellbarros.dto.ProdutoResponseDTO;
import com.github.rafaellbarros.page.Pageable;
import com.github.rafaellbarros.page.SortDirection;
import com.github.rafaellbarros.resource.api.ProdutoApi;
import com.github.rafaellbarros.service.ProdutoService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;


@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource implements ProdutoApi {

    @Inject
    private ProdutoService service;


    @Override
    public Response listarPaginado(int page, int size, String ordination, String direction) {
        try {
            Pageable pageable = Pageable.builder()
                    .page(page)
                    .size(size)
                    .ordination(ordination)
                    .direction(SortDirection.valueOf(direction.toUpperCase()))
                    .build();

            PageResposeDTO<ProdutoResponseDTO> resultado = service.listarTodosPaginado(pageable);
            return Response.ok(resultado).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Direção de ordenação inválida. Use ASC ou DESC")
                    .build();
        }
    }

    @Override
    public Response pesquisar(String nome, String descricao, BigDecimal precoMin, BigDecimal precoMax, int page, int size, String ordination, String direction) {
        try {
            ProdutoFiltroDTO filtro = new ProdutoFiltroDTO();
            filtro.setNome(nome);
            filtro.setDescricao(descricao);
            filtro.setPrecoMin(precoMin);
            filtro.setPrecoMax(precoMax);

            Pageable pageable = Pageable.builder()
                    .page(page)
                    .size(size)
                    .ordination(ordination)
                    .direction(SortDirection.valueOf(direction.toUpperCase()))
                    .build();

            PageResposeDTO<ProdutoResponseDTO> resultado = service.pesquisarComFiltros(filtro, pageable);
            return Response.ok(resultado).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Parâmetros inválidos: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public Response listarTodos() {
        List<ProdutoResponseDTO> produtos = service.listarTodos();
        return Response.ok(produtos).build();
    }

    @Override
    public Response buscarPorId(Long id) {
        var produto = service.buscarPorId(id);
        return Response.ok(produto).build();
    }

    @Override
    public Response criar(ProdutoRequestDTO produtoDTO) {
        var produtoSalvo = service.salvar(produtoDTO);
        return Response.created(URI.create("/produtos/" + produtoSalvo.getId()))
                .entity(produtoSalvo)
                .build();
    }

    @Override
    public Response atualizar(Long id, ProdutoRequestDTO produtoDTO) {
        var produtoAtualizado = service.atualizar(id, produtoDTO);
        return Response.ok(produtoAtualizado).build();
    }

    @Override
    public Response remover(Long id) {
        service.remover(id);
        return Response.noContent().build();
    }
}