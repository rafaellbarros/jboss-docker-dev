package com.github.rafaellbarros.repository;

import com.github.rafaellbarros.dto.ProdutoFiltroDTO;
import com.github.rafaellbarros.model.Produto;
import com.github.rafaellbarros.page.Page;
import com.github.rafaellbarros.page.Pageable;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
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

    public Page<Produto> pesquisarComFiltros(ProdutoFiltroDTO filtro, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Produto> cq = cb.createQuery(Produto.class);
        Root<Produto> produto = cq.from(Produto.class);

        // Aplicar filtros
        List<Predicate> predicates = construirPredicados(filtro, cb, produto);

        // Adicionar predicados à query
        cq.where(predicates.toArray(new Predicate[0]));

        // Aplicar ordenação
        aplicarOrdenacao(pageable, cb, cq, produto);

        // Executar query paginada
        List<Produto> resultados = executarQueryPaginada(cq, pageable);

        // Contar total de resultados
        long total = contarTotalResultados(filtro);

        return new Page<>(resultados, pageable.getPage(), pageable.getSize(), total);
    }

    public Page<Produto> listarTodosPaginado(Pageable pageable) {
        return pesquisarComFiltros(new ProdutoFiltroDTO(), pageable);
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

    private List<Predicate> construirPredicados(ProdutoFiltroDTO filtro, CriteriaBuilder cb, Root<Produto> produto) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotBlank(filtro.getNome())) {
            predicates.add(cb.like(
                    cb.lower(produto.get("nome")),
                    "%" + filtro.getNome().toLowerCase() + "%"
            ));
        }

        if (StringUtils.isNotBlank(filtro.getDescricao())) {
            predicates.add(cb.like(
                    cb.lower(produto.get("descricao")),
                    "%" + filtro.getDescricao().toLowerCase() + "%"
            ));
        }

        if (filtro.getPrecoMin() != null) {
            predicates.add(cb.greaterThanOrEqualTo(
                    produto.get("preco"),
                    filtro.getPrecoMin()
            ));
        }

        if (filtro.getPrecoMax() != null) {
            predicates.add(cb.lessThanOrEqualTo(
                    produto.get("preco"),
                    filtro.getPrecoMax()
            ));
        }

        return predicates;
    }

    private void aplicarOrdenacao(Pageable pageable, CriteriaBuilder cb,
                                  CriteriaQuery<Produto> cq, Root<Produto> produto) {
        if (pageable.hasOrdination()) {
            Path<Object> campoOrdenacao = produto.get(pageable.getOrdination());
            Order ordem = pageable.isAscending()
                    ? cb.asc(campoOrdenacao)
                    : cb.desc(campoOrdenacao);
            cq.orderBy(ordem);
        }
    }

    private List<Produto> executarQueryPaginada(CriteriaQuery<Produto> cq, Pageable pageable) {
        return em.createQuery(cq)
                .setFirstResult(pageable.getFirstResult())
                .setMaxResults(pageable.getSize())
                .getResultList();
    }

    private long contarTotalResultados(ProdutoFiltroDTO filtro) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Produto> produto = countQuery.from(Produto.class);

        countQuery.select(cb.count(produto));

        List<Predicate> predicates = construirPredicados(filtro, cb, produto);
        if (!predicates.isEmpty()) {
            countQuery.where(predicates.toArray(new Predicate[0]));
        }

        return em.createQuery(countQuery).getSingleResult();
    }
}