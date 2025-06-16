package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.dto.HorarioProfessorFilter;
import com.dancesys.dancesys.entity.HorarioProfessor;
import com.dancesys.dancesys.infra.CriterialUtils;
import com.dancesys.dancesys.infra.PaginatedResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HorarioProfessorRepositoryCustom {
    private EntityManager em;
    public HorarioProfessorRepositoryCustom(EntityManager em) {
        this.em = em;
    }


    public PaginatedResponse<HorarioProfessor> buscar(HorarioProfessorFilter filtro) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<HorarioProfessor> query = cb.createQuery(HorarioProfessor.class);
        Root<HorarioProfessor> root = query.from(HorarioProfessor.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filtro.getDiasSemana() != null && !filtro.getDiasSemana().isEmpty()) {
            predicates.add(root.get("diaSemana").in(filtro.getDiasSemana()));
        }

        if (filtro.getProfessores() != null && !filtro.getProfessores().isEmpty()) {
            predicates.add(root.get("idProfessor").get("id").in(filtro.getProfessores()));
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
        TypedQuery<HorarioProfessor> typedQuery = em.createQuery(query);

        if (filtro.getTamanho() != null && filtro.getTamanho() > 0) {
            int pagina = filtro.getPagina() != null ? filtro.getPagina() : 0;
            typedQuery.setFirstResult(pagina * filtro.getTamanho());
            typedQuery.setMaxResults(filtro.getTamanho());
        }

        List<HorarioProfessor> resultado = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<HorarioProfessor> countRoot = countQuery.from(HorarioProfessor.class);
        countQuery.select(cb.count(countRoot));

        List<Predicate> countPredicates = new ArrayList<>();

        if (filtro.getDiasSemana() != null && !filtro.getDiasSemana().isEmpty()) {
            countPredicates.add(countRoot.get("diaSemana").in(filtro.getDiasSemana()));
        }

        if (filtro.getProfessores() != null && !filtro.getProfessores().isEmpty()) {
            countPredicates.add(countRoot.get("idProfessor").get("id").in(filtro.getProfessores()));
        }

        countQuery.where(cb.and(countPredicates.toArray(new Predicate[0])));
        Long total = em.createQuery(countQuery).getSingleResult();

        return new PaginatedResponse<>(resultado, total);
    }
}
