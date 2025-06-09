package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.dto.AulaExperimentalFilter;
import com.dancesys.dancesys.entity.AulaExperimental;
import com.dancesys.dancesys.entity.AulaOcorrencia;
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
public class AulaExperimentalRepositoryCustom {
    private final EntityManager em;

    public AulaExperimentalRepositoryCustom(EntityManager em) {
        this.em = em;
    }

    public PaginatedResponse<AulaExperimental> buscar(AulaExperimentalFilter filtro) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<AulaExperimental> query = cb.createQuery(AulaExperimental.class);
        Root<AulaExperimental> root = query.from(AulaExperimental.class);

        List<Predicate> predicates = new ArrayList<>();

        if(filtro.getDataInicial() != null  && filtro.getDataFinal() == null) {
            LocalDate data = filtro.getDataInicial();
            LocalDateTime startOfDay = data.atStartOfDay();

            predicates.add(cb.greaterThanOrEqualTo(root.get("dataHorarioInicio"), startOfDay));
        }

        if(filtro.getDataFinal() != null  && filtro.getDataInicial() == null) {
            LocalDate data = filtro.getDataFinal();
            LocalDateTime endOfDay = data.atTime(LocalTime.MAX);

            predicates.add(cb.lessThanOrEqualTo(root.get("dataHorarioInicio"), endOfDay));
        }

        if(filtro.getDataFinal() != null  && filtro.getDataInicial() != null) {
            LocalDate dataIn = filtro.getDataInicial();
            LocalDateTime startOfDay = dataIn.atTime(LocalTime.MIN);

            LocalDate dataFim = filtro.getDataFinal();
            LocalDateTime endOfDay = dataFim.atTime(LocalTime.MAX);

            predicates.add(cb.between(root.get("dataHorarioInicio"), startOfDay, endOfDay));
        }

        if(filtro.getCpf() != null){
            predicates.add(cb.like(root.get("cpf"), "%" + filtro.getCpf() + "%"));
        }

        if(filtro.getIdProfessor() != null){
            predicates.add(cb.equal(root.get("idProfessor").get("id"), filtro.getIdProfessor()));
        }

        if(filtro.getSituacao() != null  && !filtro.getSituacao().isEmpty()){
            predicates.add(root.get("situacao").in(filtro.getSituacao()));
        }

        if(filtro.getMotivos() != null && !filtro.getMotivos().isEmpty()){
            predicates.add(root.get("motivo").in(filtro.getMotivos()));
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
        TypedQuery<AulaExperimental> typedQuery = em.createQuery(query);

        if (filtro.getTamanho() != null && filtro.getTamanho() > 0) {
            int pagina = filtro.getPagina() != null ? filtro.getPagina() : 0;
            typedQuery.setFirstResult(pagina * filtro.getTamanho());
            typedQuery.setMaxResults(filtro.getTamanho());
        }

        List<AulaExperimental> resultado = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<AulaExperimental> countRoot = countQuery.from(AulaExperimental.class);
        countQuery.select(cb.count(countRoot));

        List<Predicate> countPredicates = new ArrayList<>();


        if(filtro.getDataInicial() != null  && filtro.getDataFinal() == null) {
            LocalDate data = filtro.getDataInicial();
            LocalDateTime startOfDay = data.atStartOfDay();

            countPredicates.add(cb.greaterThanOrEqualTo(countRoot.get("dataHorarioInicio"), startOfDay));
        }

        if(filtro.getDataFinal() != null  && filtro.getDataInicial() == null) {
            LocalDate data = filtro.getDataFinal();
            LocalDateTime endOfDay = data.atTime(LocalTime.MAX);

            countPredicates.add(cb.lessThanOrEqualTo(countRoot.get("dataHorarioInicio"), endOfDay));
        }

        if(filtro.getDataFinal() != null  && filtro.getDataInicial() != null) {
            LocalDate dataIn = filtro.getDataInicial();
            LocalDateTime startOfDay = dataIn.atTime(LocalTime.MIN);

            LocalDate dataFim = filtro.getDataFinal();
            LocalDateTime endOfDay = dataFim.atTime(LocalTime.MAX);

            countPredicates.add(cb.between(countRoot.get("dataHorarioInicio"), startOfDay, endOfDay));
        }

        if(filtro.getCpf() != null){
            countPredicates.add(cb.like(countRoot.get("cpf"), "%" + filtro.getCpf() + "%"));
        }

        if(filtro.getIdProfessor() != null){
            countPredicates.add(cb.equal(countRoot.get("idProfessor").get("id"), filtro.getIdProfessor()));
        }

        if(filtro.getSituacao() != null  && !filtro.getSituacao().isEmpty()){
            countPredicates.add(countRoot.get("situacao").in(filtro.getSituacao()));
        }

        if(filtro.getMotivos() != null && !filtro.getMotivos().isEmpty()){
            countPredicates.add(countRoot.get("motivo").in(filtro.getMotivos()));
        }

        countQuery.where(cb.and(countPredicates.toArray(new Predicate[0])));
        Long total = em.createQuery(countQuery).getSingleResult();

        return new PaginatedResponse<>(resultado, total);
    }
}
