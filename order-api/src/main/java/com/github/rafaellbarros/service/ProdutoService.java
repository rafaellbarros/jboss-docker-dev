package com.github.rafaellbarros.service;

import com.github.rafaellbarros.exception.NotFoundException;
import com.github.rafaellbarros.model.Produto;
import com.github.rafaellbarros.repository.ProdutoRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@Stateless
public class ProdutoService {

    @Inject
    private ProdutoRepository repository;

    public List<Produto> listarTodos() {
        return repository.listarTodos();
    }

    public Produto buscarPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado com o ID: " + id));
    }

    public Produto salvar(Produto produto) {
        Objects.requireNonNull(produto, "Produto não pode ser nulo");
        return repository.salvar(produto);
    }


    public Produto atualizar(Produto produto) {
        Objects.requireNonNull(produto, "Produto não pode ser nulo");
        Produto existente = this.buscarPorId(produto.getId());
        existente.setId(produto.getId());
        existente.setNome(produto.getNome());
        existente.setPreco(produto.getPreco());
        return this.salvar(existente);

    }

    public void remover(Long id) {
        Produto produto = buscarPorId(id);
        repository.remover(produto.getId());
    }
}
