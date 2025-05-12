package com.github.rafaellbarros.service;

import com.github.rafaellbarros.exception.BusinessException;
import com.github.rafaellbarros.model.Produto;
import com.github.rafaellbarros.repository.ProdutoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ProdutoService {

    @Inject
    private ProdutoRepository repository;

    public List<Produto> listarTodos() {
        return repository.listarTodos();
    }

    public Produto buscarPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado com o ID: " + id));
    }

    @Transactional
    public Produto salvar(Produto produto) {
        Objects.requireNonNull(produto, "Produto não pode ser nulo");
        return repository.salvar(produto);
    }

    @Transactional
    public void remover(Long id) {
        Produto produto = buscarPorId(id);
        repository.remover(produto.getId());
    }
}
