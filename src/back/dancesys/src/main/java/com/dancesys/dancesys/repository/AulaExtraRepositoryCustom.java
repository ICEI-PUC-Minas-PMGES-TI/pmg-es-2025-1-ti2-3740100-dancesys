package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.dto.AulaExtraFilter;
import com.dancesys.dancesys.entity.AulaExtra;
import com.dancesys.dancesys.infra.CriterialUtils;
import com.dancesys.dancesys.infra.PaginatedResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AulaExtraRepositoryCustom {
    private EntityManager em;

    public AulaExtraRepositoryCustom(EntityManager em) {
        this.em = em;
    }

    public PaginatedResponse<AulaExtra> buscar(AulaExtraFilter filtro){
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<AulaExtra> query = cb.createQuery(AulaExtra.class);
        Root<AulaExtra> root = query.from(AulaExtra.class);

        List<Predicate> predicates = new ArrayList<>();


        if(filtro.getDataInicio() != null && filtro.getDataFim() == null){
            LocalDate data = filtro.getDataInicio();
            LocalDateTime startOfDay = data.atStartOfDay();
            LocalDateTime endOfDay = data.atTime(LocalTime.MAX);

            predicates.add(cb.between(root.get("horarioInicio"), startOfDay, endOfDay));
        }

        if(filtro.getDataInicio() != null && filtro.getDataFim() != null){
            LocalDate dataIn = filtro.getDataInicio();
            LocalDate dataFim = filtro.getDataFim();
            LocalDateTime startOfDay = dataIn.atStartOfDay();
            LocalDateTime endOfDay = dataFim.atTime(LocalTime.MAX);

            predicates.add(cb.between(root.get("horarioInicio"), startOfDay, endOfDay));
        }

        if(filtro.getIdProfessor() != null){
            predicates.add(cb.equal(root.get("idProfessor").get("id"), filtro.getIdAluno()));
        }

        if(filtro.getIdAluno() != null){
            predicates.add(cb.equal(root.get("idAluno").get("id"), filtro.getIdAluno()));
        }

        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty()){
            predicates.add(root.get("situacao").in(filtro.getStatus()));
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
        TypedQuery<AulaExtra> typedQuery = em.createQuery(query);

        if (filtro.getTamanho() != null && filtro.getTamanho() > 0) {
            int pagina = filtro.getPagina() != null ? filtro.getPagina() : 0;
            typedQuery.setFirstResult(pagina * filtro.getTamanho());
            typedQuery.setMaxResults(filtro.getTamanho());
        }

        List<AulaExtra> resultado = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<AulaExtra> countRoot = countQuery.from(AulaExtra.class);
        countQuery.select(cb.count(countRoot));

        List<Predicate> countPredicates = new ArrayList<>();

        if(filtro.getDataInicio() != null && filtro.getDataFim() == null){
            LocalDate data = filtro.getDataInicio();
            LocalDateTime startOfDay = data.atStartOfDay();
            LocalDateTime endOfDay = data.atTime(LocalTime.MAX);

            countPredicates.add(cb.between(countRoot.get("horarioInicio"), startOfDay, endOfDay));
        }

        if(filtro.getDataInicio() != null && filtro.getDataFim() != null){
            LocalDate dataIn = filtro.getDataInicio();
            LocalDate dataFim = filtro.getDataFim();
            LocalDateTime startOfDay = dataIn.atStartOfDay();
            LocalDateTime endOfDay = dataFim.atTime(LocalTime.MAX);

            countPredicates.add(cb.between(countRoot.get("horarioInicio"), startOfDay, endOfDay));
        }

        if(filtro.getIdProfessor() != null){
            countPredicates.add(cb.equal(countRoot.get("idProfessor").get("id"), filtro.getIdAluno()));
        }

        if(filtro.getIdAluno() != null){
            countPredicates.add(cb.equal(countRoot.get("idAluno").get("id"), filtro.getIdAluno()));
        }

        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty()){
            countPredicates.add(countRoot.get("situacao").in(filtro.getStatus()));
        }

        countQuery.where(cb.and(countPredicates.toArray(new Predicate[0])));
        Long total = em.createQuery(countQuery).getSingleResult();

        return new PaginatedResponse<>(resultado, total);
    }
}
