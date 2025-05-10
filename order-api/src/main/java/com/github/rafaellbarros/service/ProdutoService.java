package com.github.rafaellbarros.service;

import com.github.rafaellbarros.model.Produto;
import com.github.rafaellbarros.repository.ProdutoRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ProdutoService {

    @Inject
    private ProdutoRepository repository;

    public List<Produto> listarTodos() {
        return repository.listarTodos();
    }

    public Produto buscarPorId(Long id) {
        Produto produto = repository.buscarPorId(id);
        if (produto == null) {
            throw new IllegalArgumentException("Produto não encontrado com o ID: " + id);
        }
        return produto;
    }

    public Produto salvar(Produto produto) {
        return repository.salvar(produto);
    }

    public Produto atualizar(Produto produto) {
        Produto existente = this.buscarPorId(produto.getId());
        existente.setId(produto.getId());
        existente.setNome(produto.getNome());
        existente.setPreco(produto.getPreco());
        return this.salvar(existente);

    }

    public void remover(Long id) {
        Produto existente = this.buscarPorId(id);
        repository.remover(existente.getId());
    }
}
