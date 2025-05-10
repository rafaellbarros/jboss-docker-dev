package com.github.rafaellbarros.repository;

import com.github.rafaellbarros.model.Produto;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
public class ProdutoRepository {
    
    @PersistenceContext
    private EntityManager em;


    public Produto salvar(Produto produto) {
        if (produto.getId() == null) {
            em.persist(produto);
            return produto;
        }
        return em.merge(produto);
    }
    
    public List<Produto> listarTodos() {
        return em.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return Optional.ofNullable(em.find(Produto.class, id));
    }

    public void remover(Long id) {
        buscarPorId(id).ifPresent(em::remove);
    }
}