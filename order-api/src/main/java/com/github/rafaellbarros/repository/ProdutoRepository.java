package com.github.rafaellbarros.repository;

import com.github.rafaellbarros.model.Produto;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProdutoRepository {
    
    @PersistenceContext
    private EntityManager em;
    
    public Produto salvar(Produto produto) {
        em.persist(produto);
        return produto;
    }
    
    public List<Produto> listarTodos() {
        return em.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
    }
    
    public Produto buscarPorId(Long id) {
        return em.find(Produto.class, id);
    }
    
    public void remover(Long id) {
        Produto produto = buscarPorId(id);
        if (produto != null) {
            em.remove(produto);
        }
    }
}