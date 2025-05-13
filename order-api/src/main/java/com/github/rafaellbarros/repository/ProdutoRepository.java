package com.github.rafaellbarros.repository;

import com.github.rafaellbarros.model.Produto;
import com.github.rafaellbarros.page.Page;
import com.github.rafaellbarros.page.Pageable;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
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

    public Page<Produto> listarTodosPaginado(Pageable pageable) {
        // Query principal
        String jpql = "SELECT p FROM Produto p";

        if (pageable.getOrdination() != null && !pageable.getOrdination().isEmpty()) {
            jpql += " ORDER BY p." + pageable.getOrdination() + " " + pageable.getDirection().name();
        }

        TypedQuery<Produto> query = em.createQuery(jpql, Produto.class);

        query.setFirstResult(pageable.getFirstResult());
        query.setMaxResults(pageable.getSize());

        List<Produto> content = query.getResultList();

        Long total = em.createQuery("SELECT COUNT(p) FROM Produto p", Long.class).getSingleResult();

        return new Page<>(
                content,
                pageable.getPage(),
                pageable.getSize(),
                total
        );
    }

    public Optional<Produto> buscarPorId(Long id) {
        return Optional.ofNullable(em.find(Produto.class, id));
    }

    public void remover(Long id) {
        buscarPorId(id).ifPresent(em::remove);
    }

    public boolean existsById(Long id) {
        return buscarPorId(id).isPresent();
    }
}