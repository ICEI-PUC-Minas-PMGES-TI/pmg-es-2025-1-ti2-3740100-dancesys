package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.dto.AlunoFilter;
import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.infra.CriterialUtils;
import com.dancesys.dancesys.infra.PaginatedResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AlunoRepositoryCustom {
    private EntityManager em;

    public AlunoRepositoryCustom(EntityManager em) {
        this.em = em;
    }

    public PaginatedResponse<Aluno> buscar(AlunoFilter filtro){

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Aluno> query = cb.createQuery(Aluno.class);
        Root<Aluno> root = query.from(Aluno.class);

        List<Predicate> predicates = new ArrayList<>();

        if(filtro.getNome() != null){
            predicates.add(cb.like(root.get("idUsuario").get("nome"), "%" + filtro.getNome() + "%"));
        }

        if(filtro.getCpf() != null){
            predicates.add(cb.like(root.get("idUsuario").get("cpf"), "%" + filtro.getCpf() + "%"));
        }

        if(filtro.getEmail() != null){
            predicates.add(cb.like(root.get("idUsuario").get("cpf"), "%" + filtro.getEmail() + "%"));
        }

        if(filtro.getTipo() != null){
            predicates.add(cb.equal(root.get("tipo"), filtro.getTipo()));
        }

        if(filtro.getStatus() != null){
            predicates.add(cb.equal(root.get("idUsuario").get("status"), filtro.getStatus()));
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
        TypedQuery<Aluno> typedQuery = em.createQuery(query);

        if (filtro.getTamanho() != null && filtro.getTamanho() > 0) {
            int pagina = filtro.getPagina() != null ? filtro.getPagina() : 0;
            typedQuery.setFirstResult(pagina * filtro.getTamanho());
            typedQuery.setMaxResults(filtro.getTamanho());
        }

        List<Aluno> resultado = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Aluno> countRoot = countQuery.from(Aluno.class);
        countQuery.select(cb.count(countRoot));

        List<Predicate> countPredicates = new ArrayList<>();

        if(filtro.getNome() != null){
            countPredicates.add(cb.like(countRoot.get("idUsuario").get("nome"), "%" + filtro.getNome() + "%"));
        }

        if(filtro.getCpf() != null){
            countPredicates.add(cb.like(countRoot.get("idUsuario").get("cpf"), "%" + filtro.getCpf() + "%"));
        }

        if(filtro.getEmail() != null){
            countPredicates.add(cb.like(countRoot.get("idUsuario").get("cpf"), "%" + filtro.getEmail() + "%"));
        }

        if(filtro.getTipo() != null){
            countPredicates.add(cb.equal(countRoot.get("tipo"), filtro.getTipo()));
        }

        if(filtro.getStatus() != null){
            countPredicates.add(cb.equal(countRoot.get("idUsuario").get("status"), filtro.getStatus()));
        }

        countQuery.where(cb.and(countPredicates.toArray(new Predicate[0])));
        Long total = em.createQuery(countQuery).getSingleResult();

        return new PaginatedResponse<>(resultado, total);
    }
}
