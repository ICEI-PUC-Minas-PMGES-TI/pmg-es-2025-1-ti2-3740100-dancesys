package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.dto.AulaFilter;
import com.dancesys.dancesys.entity.Aula;
import com.dancesys.dancesys.infra.PaginatedResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AulaRepositoryCustom {
    private EntityManager em;

    public AulaRepositoryCustom(EntityManager em) {this.em = em;}

    public PaginatedResponse<Aula> buscarAulas(AulaFilter filtro) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Aula> query = cb.createQuery(Aula.class);
        Root<Aula> root = query.from(Aula.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filtro.getDias() != null && !filtro.getDias().isEmpty()) {
            predicates.add(root.get("diaSemana").in(filtro.getDias()));
        }

        if (filtro.getProfessores() != null && !filtro.getProfessores().isEmpty()) {
            predicates.add(root.get("idProfessor").get("id").in(filtro.getProfessores()));
        }

        if(filtro.getModalidades() != null && !filtro.getModalidades().isEmpty()) {
            predicates.add(root.get("idModalidade").get("id").in(filtro.getModalidades()));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        TypedQuery<Aula> typedQuery = em.createQuery(query);

        if (filtro.getTamanho() != null && filtro.getTamanho() > 0) {
            int pagina = filtro.getPagina() != null ? filtro.getPagina() : 0;
            typedQuery.setFirstResult(pagina * filtro.getTamanho());
            typedQuery.setMaxResults(filtro.getTamanho());
        }

        List<Aula> resultado = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Aula> countRoot = countQuery.from(Aula.class);
        countQuery.select(cb.count(countRoot));

        List<Predicate> countPredicates = new ArrayList<>();

        if (filtro.getDias() != null && !filtro.getDias().isEmpty()) {
            countPredicates.add(countRoot.get("diaSemana").in(filtro.getDias()));
        }

        if (filtro.getProfessores() != null && !filtro.getProfessores().isEmpty()) {
            countPredicates.add(countRoot.get("idProfessor").get("id").in(filtro.getProfessores()));
        }

        if (filtro.getModalidades() != null && !filtro.getModalidades().isEmpty()) {
            countPredicates.add(countRoot.get("idModalidade").get("id").in(filtro.getModalidades()));
        }

        countQuery.where(cb.and(countPredicates.toArray(new Predicate[0])));
        Long total = em.createQuery(countQuery).getSingleResult();

        return new PaginatedResponse<>(resultado, total);
    }
}
