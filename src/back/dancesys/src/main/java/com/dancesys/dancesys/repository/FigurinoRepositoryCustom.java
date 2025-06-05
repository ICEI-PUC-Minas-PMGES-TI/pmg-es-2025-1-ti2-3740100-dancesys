package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.dto.FigurinoFilter;
import com.dancesys.dancesys.entity.Figurino;
import com.dancesys.dancesys.infra.CriterialUtils;
import com.dancesys.dancesys.infra.PaginatedResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FigurinoRepositoryCustom {

    private final EntityManager em;

    public FigurinoRepositoryCustom(EntityManager em) {
        this.em = em;
    }

    public PaginatedResponse<Figurino> buscar(FigurinoFilter filtro){
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Figurino> query = cb.createQuery(Figurino.class);
        Root<Figurino> root = query.from(Figurino.class);

        List<Predicate> predicates = new ArrayList<>();

        if(filtro.getNome()!=null && !filtro.getNome().isEmpty()){
            predicates.add(cb.like(root.get("nome"), "%" + filtro.getNome() + "%"));
        }

        if(filtro.getValor() != null){
            predicates.add(cb.equal(root.get("valor"), filtro.getValor()));
        }

        if(filtro.getTipo() !=null){
            predicates.add(cb.equal(root.get("tipo"), filtro.getTipo()));
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
        TypedQuery<Figurino> typedQuery = em.createQuery(query);

        if (filtro.getTamanho() != null && filtro.getTamanho() > 0) {
            int pagina = filtro.getPagina() != null ? filtro.getPagina() : 0;
            typedQuery.setFirstResult(pagina * filtro.getTamanho());
            typedQuery.setMaxResults(filtro.getTamanho());
        }

        List<Figurino> resultado = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Figurino> countRoot = countQuery.from(Figurino.class);
        countQuery.select(cb.count(countRoot));

        List<Predicate> countPredicates = new ArrayList<>();


        if(filtro.getNome()!=null && !filtro.getNome().isEmpty()){
            countPredicates.add(cb.like(countRoot.get("nome"), "%" + filtro.getNome() + "%"));
        }

        if(filtro.getValor() != null){
            countPredicates.add(cb.equal(countRoot.get("valor"), filtro.getValor()));
        }

        if(filtro.getTipo() !=null){
            countPredicates.add(cb.equal(countRoot.get("tipo"), filtro.getTipo()));
        }


        countQuery.where(cb.and(countPredicates.toArray(new Predicate[0])));
        Long total = em.createQuery(countQuery).getSingleResult();

        return new PaginatedResponse<>(resultado, total);
    }
}
