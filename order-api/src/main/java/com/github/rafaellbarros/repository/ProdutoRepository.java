package com.github.rafaellbarros.repository;

import com.github.rafaellbarros.dto.ProdutoFiltroDTO;
import com.github.rafaellbarros.model.Produto;
import com.github.rafaellbarros.page.Page;
import com.github.rafaellbarros.page.Pageable;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        // Construção da query dinâmica
        StringBuilder jpql = new StringBuilder("SELECT p FROM Produto p WHERE 1=1");
        Map<String, Object> parametros = new HashMap<>();

        // Aplicar filtros
        if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
            jpql.append(" AND LOWER(p.nome) LIKE LOWER(:nome)");
            parametros.put("nome", "%" + filtro.getNome() + "%");
        }

        if (filtro.getDescricao() != null && !filtro.getDescricao().isEmpty()) {
            jpql.append(" AND LOWER(p.descricao) LIKE LOWER(:descricao)");
            parametros.put("descricao", "%" + filtro.getDescricao() + "%");
        }

        if (filtro.getPrecoMin() != null) {
            jpql.append(" AND p.preco >= :precoMin");
            parametros.put("precoMin", filtro.getPrecoMin());
        }

        if (filtro.getPrecoMax() != null) {
            jpql.append(" AND p.preco <= :precoMax");
            parametros.put("precoMax", filtro.getPrecoMax());
        }

        // Ordenação (apenas para a query principal)
        String jpqlOrdenacao = "";
        if (pageable.hasOrdination()) {
            jpqlOrdenacao = " ORDER BY p." + pageable.getOrdination() + " " + pageable.getDirection().name();
        }

        // Criar e executar query para os resultados
        TypedQuery<Produto> query = em.createQuery(jpql.toString() + jpqlOrdenacao, Produto.class);
        parametros.forEach(query::setParameter);

        query.setFirstResult(pageable.getFirstResult());
        query.setMaxResults(pageable.getSize());

        List<Produto> content = query.getResultList();

        // Criar query para contar o total de resultados (sem ordenação)
        String countJpql = jpql.toString().replace("SELECT p FROM Produto p", "SELECT COUNT(p) FROM Produto p");
        TypedQuery<Long> countQuery = em.createQuery(countJpql, Long.class);
        parametros.forEach(countQuery::setParameter);

        Long total = countQuery.getSingleResult();

        return new Page<>(
                content,
                pageable.getPage(),
                pageable.getSize(),
                total
        );
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
}