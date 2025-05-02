package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.dto.ProfessorFilter;
import com.dancesys.dancesys.entity.Professor;
import com.dancesys.dancesys.infra.PaginatedResponse;
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
public class ProfessorCustomRepository {
    EntityManager em;
    public ProfessorCustomRepository(EntityManager em) {this.em = em;}

    public PaginatedResponse<Professor> buscar(ProfessorFilter filtro) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Professor> query = cb.createQuery(Professor.class);
        Root<Professor> root = query.from(Professor.class);

        List<Predicate> predicates = new ArrayList<>();

        if(filtro.getNome() != null &&  !filtro.getNome().equals("")){
            predicates.add(cb.like(root.get("idUsuario").get("nome"), "%" + filtro.getNome() + "%"));
        }

        if(filtro.getCpf() != null &&  !filtro.getCpf().equals("")){
            predicates.add(cb.like(root.get("idUsuario").get("cpf"),"%" + filtro.getCpf() + "%"));
        }

        if(filtro.getEmail() != null &&  !filtro.getEmail().equals("")){
            predicates.add(cb.like(root.get("idUsuario").get("email"),"%" + filtro.getEmail() + "%"));
        }

        if(filtro.getStatus() != null){
            predicates.add(cb.equal(root.get("idUsuario").get("status"),filtro.getStatus()));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        TypedQuery<Professor> typedQuery = em.createQuery(query);

        if (filtro.getTamanho() != null && filtro.getTamanho() > 0) {
            int pagina = filtro.getPagina() != null ? filtro.getPagina() : 0;
            typedQuery.setFirstResult(pagina * filtro.getTamanho());
            typedQuery.setMaxResults(filtro.getTamanho());
        }

        List<Professor> resultado = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Professor> countRoot = countQuery.from(Professor.class);
        countQuery.select(cb.count(countRoot));

        List<Predicate> countPredicates = new ArrayList<>();

        if(filtro.getNome() != null &&  !filtro.getNome().equals("")){
            countPredicates.add(cb.like(countRoot.get("idUsuario").get("nome"), "%" + filtro.getNome() + "%"));
        }

        if(filtro.getCpf() != null &&  !filtro.getCpf().equals("")){
            countPredicates.add(cb.like(countRoot.get("idUsuario").get("cpf"), "%" + filtro.getCpf() + "%"));
        }

        if(filtro.getEmail() != null &&  !filtro.getEmail().equals("")){
            countPredicates.add(cb.like(countRoot.get("idUsuario").get("email"), "%" + filtro.getEmail() + "%"));
        }

        if(filtro.getStatus() != null){
            countPredicates.add(cb.equal(countRoot.get("idUsuario").get("status"),filtro.getStatus()));
        }

        countQuery.where(cb.and(countPredicates.toArray(new Predicate[0])));
        Long total = em.createQuery(countQuery).getSingleResult();

        return new PaginatedResponse<>(resultado, total);
    }
}
