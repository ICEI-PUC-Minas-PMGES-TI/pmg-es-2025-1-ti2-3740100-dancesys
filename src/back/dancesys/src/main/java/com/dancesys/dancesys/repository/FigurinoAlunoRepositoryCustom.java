package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.dto.FigurinoAlunoFilter;
import com.dancesys.dancesys.entity.FigurinoApresentacao;
import com.dancesys.dancesys.infra.CriterialUtils;
import com.dancesys.dancesys.infra.PaginatedResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FigurinoAlunoRepositoryCustom {
    private EntityManager em;

    public FigurinoAlunoRepositoryCustom(EntityManager em) {
        this.em = em;
    }

    public PaginatedResponse<FigurinoApresentacao> buscar(FigurinoAlunoFilter filtro){
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<FigurinoApresentacao> query = cb.createQuery(FigurinoApresentacao.class);
        Root<FigurinoApresentacao> root = query.from(FigurinoApresentacao.class);

        List<Predicate> predicates = new ArrayList<>();

        if(filtro.getIdAluno() != null){
            predicates.add(cb.equal(root.get("idAluno").get("id"), filtro.getIdAluno()));
        }

        if(filtro.getIdFigurino() != null){
            predicates.add(cb.equal(root.get("idFigurino").get("id"), filtro.getIdFigurino()));
        }

        if(filtro.getIdEvento() != null){
            predicates.add(cb.equal(root.get("idApresentacaoEvento").get("idEvento"), filtro.getIdEvento()));
        }

        if(filtro.getIdApresentacao() != null){
            predicates.add(cb.equal(root.get("idApresentacaoEvento").get("id"), filtro.getIdApresentacao()));
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
        TypedQuery<FigurinoApresentacao> typedQuery = em.createQuery(query);

        if (filtro.getTamanho() != null && filtro.getTamanho() > 0) {
            int pagina = filtro.getPagina() != null ? filtro.getPagina() : 0;
            typedQuery.setFirstResult(pagina * filtro.getTamanho());
            typedQuery.setMaxResults(filtro.getTamanho());
        }

        List<FigurinoApresentacao> resultado = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<FigurinoApresentacao> countRoot = countQuery.from(FigurinoApresentacao.class);
        countQuery.select(cb.count(countRoot));

        List<Predicate> countPredicates = new ArrayList<>();

        if(filtro.getIdAluno() != null){
            countPredicates.add(cb.equal(countRoot.get("idAluno").get("id"), filtro.getIdAluno()));
        }

        if(filtro.getIdFigurino() != null){
            countPredicates.add(cb.equal(countRoot.get("idFigurino").get("id"), filtro.getIdFigurino()));
        }

        if(filtro.getIdEvento() != null){
            countPredicates.add(cb.equal(countRoot.get("idApresentacaoEvento").get("idEvento"), filtro.getIdEvento()));
        }

        if(filtro.getIdApresentacao() != null){
            countPredicates.add(cb.equal(countRoot.get("idApresentacaoEvento").get("id"), filtro.getIdApresentacao()));
        }

        countQuery.where(cb.and(countPredicates.toArray(new Predicate[0])));
        Long total = em.createQuery(countQuery).getSingleResult();

        return new PaginatedResponse<>(resultado, total);
    }
}
