package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.dto.ApresentacaoFilter;
import com.dancesys.dancesys.entity.ApresentacaoEvento;
import com.dancesys.dancesys.infra.CriterialUtils;
import com.dancesys.dancesys.infra.PaginatedResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ApresentacaoEventoRepositoryCustom {
    private EntityManager em;
    public ApresentacaoEventoRepositoryCustom(EntityManager em) {
        this.em = em;
    }

    public PaginatedResponse<ApresentacaoEvento> buscar(ApresentacaoFilter filtro) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<ApresentacaoEvento> query = cb.createQuery(ApresentacaoEvento.class);
        Root<ApresentacaoEvento> root = query.from(ApresentacaoEvento.class);

        List<Predicate> predicates = new ArrayList<>();

        if(filtro.getNome() != null && !filtro.getNome().isEmpty()) {
            predicates.add(cb.like(root.get("nome"), "%" + filtro.getNome() + "%"));
        }

        if(filtro.getIdEvento() != null) {
            predicates.add(cb.equal(root.get("idEvento").get("id"), filtro.getIdEvento()));
        }

        if(filtro.getAlunos() != null && !filtro.getAlunos().isEmpty()) {
            predicates.add(root.get("alunos").get("idAluno").get("id").in(filtro.getAlunos()));
        }

        if (filtro.getOrderBy() != null && !filtro.getOrderBy().isEmpty()) {
            Path<?> campoOrdenacao = CriterialUtils.getPath(root, filtro.getOrderBy());

            if (filtro.getOrder().equalsIgnoreCase("asc")) {
                query.orderBy(cb.asc(campoOrdenacao));
            } else {
                query.orderBy(cb.desc(campoOrdenacao));
            }
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        TypedQuery<ApresentacaoEvento> typedQuery = em.createQuery(query);

        if (filtro.getTamanho() != null && filtro.getTamanho() > 0) {
            int pagina = filtro.getPagina() != null ? filtro.getPagina() : 0;
            typedQuery.setFirstResult(pagina * filtro.getTamanho());
            typedQuery.setMaxResults(filtro.getTamanho());
        }

        List<ApresentacaoEvento> resultado = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<ApresentacaoEvento> countRoot = countQuery.from(ApresentacaoEvento.class);
        countQuery.select(cb.count(countRoot));

        List<Predicate> countPredicates = new ArrayList<>();

        if(filtro.getNome() != null && !filtro.getNome().isEmpty()) {
            countPredicates.add(cb.like(countRoot.get("nome"), "%" + filtro.getNome() + "%"));
        }

        if(filtro.getIdEvento() != null) {
            countPredicates.add(cb.equal(countRoot.get("idEvento").get("id"), filtro.getIdEvento()));
        }

        if(filtro.getAlunos() != null && !filtro.getAlunos().isEmpty()) {
            countPredicates.add(countRoot.get("alunos").get("idAluno").get("id").in(filtro.getAlunos()));
        }
        countQuery.where(cb.and(countPredicates.toArray(new Predicate[0])));
        Long total = em.createQuery(countQuery).getSingleResult();

        return new PaginatedResponse<>(resultado, total);
    }
}
