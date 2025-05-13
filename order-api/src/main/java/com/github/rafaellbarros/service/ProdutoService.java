package com.github.rafaellbarros.service;

import com.github.rafaellbarros.dto.PageResposeDTO;
import com.github.rafaellbarros.dto.ProdutoFiltroDTO;
import com.github.rafaellbarros.dto.ProdutoRequestDTO;
import com.github.rafaellbarros.dto.ProdutoResponseDTO;
import com.github.rafaellbarros.exception.BusinessNotException;
import com.github.rafaellbarros.mapper.ProdutoMapper;
import com.github.rafaellbarros.model.Produto;
import com.github.rafaellbarros.page.Page;
import com.github.rafaellbarros.page.Pageable;
import com.github.rafaellbarros.repository.ProdutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ProdutoService {

    private static final Logger LOG = LoggerFactory.getLogger(ProdutoService.class);

    @Inject
    private ProdutoRepository repository;

    @Inject
    ProdutoMapper mapper;

    public List<ProdutoResponseDTO> listarTodos() {
        List<Produto> produtos = repository.listarTodos();
        LOG.info("listarTodos : {}", produtos.size());
        return mapper.toDTOList(produtos);
    }

    public PageResposeDTO<ProdutoResponseDTO> listarTodosPaginado(Pageable pageable) {
        Page<Produto> page = repository.listarTodosPaginado(pageable);
        LOG.info("listarTodosPaginado : {}", page.getTotalElements());
        List<ProdutoResponseDTO> dtos = mapper.toDTOList(page.getContent());

        return new PageResposeDTO<>(
                dtos,
                page.getCurrentPage(),
                page.getPageSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public PageResposeDTO<ProdutoResponseDTO> pesquisarComFiltros(ProdutoFiltroDTO filtro, Pageable pageable) {
        Page<Produto> page = repository.pesquisarComFiltros(filtro, pageable);
        LOG.info("pesquisarComFiltros : encontrados {} produtos", page.getTotalElements());
        List<ProdutoResponseDTO> dtos = mapper.toDTOList(page.getContent());

        return new PageResposeDTO<>(
                dtos,
                page.getCurrentPage(),
                page.getPageSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public ProdutoResponseDTO buscarPorId(final Long id) {
        LOG.info("buscarPorId : {}", id);
        return repository.buscarPorId(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new BusinessNotException("Produto não encontrado com o ID: " + id));
    }

    @Transactional
    public ProdutoResponseDTO salvar(final ProdutoRequestDTO produtoDTO) {
        Objects.requireNonNull(produtoDTO, "Produto não pode ser nulo");
        Produto produto = mapper.toEntity(produtoDTO);
        Produto produtoSalvo = repository.salvar(produto);
        LOG.info("salvar : {}", produtoSalvo);
        return mapper.toDTO(produtoSalvo);
    }

    @Transactional
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO produtoDTO) {
        Produto produto = repository.buscarPorId(id)
                .orElseThrow(() -> new BusinessNotException("Produto não encontrado com o ID: " + id));

        mapper.fromDTO(produtoDTO, produto);
        Produto produtoAtualizado = repository.salvar(produto);
        LOG.info("atualizar : {}", produtoAtualizado);
        return mapper.toDTO(produtoAtualizado);
    }

    @Transactional
    public void remover(Long id) {
        LOG.info("remover : {}", id);
        if (!repository.existsById(id)) {
            throw new BusinessNotException("Produto não encontrado com o ID: " + id);
        }
        repository.remover(id);
    }
}
