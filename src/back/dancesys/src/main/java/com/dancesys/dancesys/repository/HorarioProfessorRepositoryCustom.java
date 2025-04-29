package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.dto.HorarioProfessorFilter;
import com.dancesys.dancesys.entity.HorarioProfessor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HorarioProfessorRepositoryCustom {
    private EntityManager em;
    public HorarioProfessorRepositoryCustom(EntityManager em) {
        this.em = em;
    }

    public List<HorarioProfessor> buscar(HorarioProfessorFilter filtro){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<HorarioProfessor> query = cb.createQuery(HorarioProfessor.class);
        Root<HorarioProfessor> root = query.from(HorarioProfessor.class);

        List<Predicate> predicates = new ArrayList<>();

        if(filtro.getDiasSemana() != null && !filtro.getDiasSemana().isEmpty()){
            predicates.add(root.get("diaSemana").in(filtro.getDiasSemana()));
        }

        if(filtro.getProfessores() != null && !filtro.getProfessores().isEmpty()){
            predicates.add(root.get("idProfessor").get("id").in(filtro.getProfessores()));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<HorarioProfessor> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }
}
