package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.dto.AulaOcorrenciaFilter;
import com.dancesys.dancesys.entity.AulaOcorrencia;
import com.dancesys.dancesys.infra.PaginatedResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import com.dancesys.dancesys.infra.CriterialUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AulaOcorrenciaRepositoryCustom {
    private final EntityManager em;

    public AulaOcorrenciaRepositoryCustom(EntityManager em) {
        this.em = em;
    }

    public PaginatedResponse<AulaOcorrencia> buscar(AulaOcorrenciaFilter filtro) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<AulaOcorrencia> query = cb.createQuery(AulaOcorrencia.class);
        Root<AulaOcorrencia> root = query.from(AulaOcorrencia.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filtro.getAlunos() != null && !filtro.getAlunos().isEmpty()) {
            predicates.add(root.get("chamada").get("idAluno").get("id").in(filtro.getAlunos()));
        }

        if (filtro.getAlunoNotIn() != null) {
            predicates.add(cb.notEqual(root.get("chamada").get("idAluno").get("id"), filtro.getAlunoNotIn()));
        }

        if (filtro.getDataInicio() != null && filtro.getDataFim() == null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("data"), filtro.getDataInicio()));
        }

        if (filtro.getDataFim() != null && filtro.getDataInicio() == null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("data"), filtro.getDataFim()));
        }

        if (filtro.getDataInicio() != null && filtro.getDataFim() != null) {
            predicates.add(cb.between(root.get("data"), filtro.getDataInicio(), filtro.getDataFim()));
        }

        if(filtro.getCodigo() != null && !filtro.getCodigo().equals("")) {
            predicates.add(cb.like(root.get("codigo"), "%" + filtro.getCodigo() + "%"));
        }

        if(filtro.getProfessores() != null && !filtro.getProfessores().isEmpty()) {
            predicates.add(root.get("idAula").get("idProfessor").get("id").in(filtro.getProfessores()));
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
        TypedQuery<AulaOcorrencia> typedQuery = em.createQuery(query);

        if (filtro.getTamanho() != null && filtro.getTamanho() > 0) {
            int pagina = filtro.getPagina() != null ? filtro.getPagina() : 0;
            typedQuery.setFirstResult(pagina * filtro.getTamanho());
            typedQuery.setMaxResults(filtro.getTamanho());
        }

        List<AulaOcorrencia> resultado = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<AulaOcorrencia> countRoot = countQuery.from(AulaOcorrencia.class);
        countQuery.select(cb.count(countRoot));

        List<Predicate> countPredicates = new ArrayList<>();

        if (filtro.getAlunos() != null && !filtro.getAlunos().isEmpty()) {
            countPredicates.add(countRoot.get("chamada").get("idAluno").get("id").in(filtro.getAlunos()));
        }

        if (filtro.getAlunoNotIn() != null) {
            countPredicates.add(cb.notEqual(countRoot.get("chamada").get("idAluno").get("id"), filtro.getAlunoNotIn()));
        }

        if (filtro.getDataInicio() != null && filtro.getDataFim() == null) {
            countPredicates.add(cb.greaterThanOrEqualTo(countRoot.get("data"), filtro.getDataInicio()));
        }

        if (filtro.getDataFim() != null && filtro.getDataInicio() == null) {
            countPredicates.add(cb.lessThanOrEqualTo(countRoot.get("data"), filtro.getDataFim()));
        }

        if (filtro.getDataInicio() != null && filtro.getDataFim() != null) {
            countPredicates.add(cb.between(countRoot.get("data"), filtro.getDataInicio(), filtro.getDataFim()));
        }

        if(filtro.getCodigo() != null && !filtro.getCodigo().equals("")) {
            countPredicates.add(cb.like(countRoot.get("codigo"), "%" + filtro.getCodigo() + "%"));
        }

        if(filtro.getProfessores() != null && !filtro.getProfessores().isEmpty()) {
            countPredicates.add(countRoot.get("idAula").get("idProfessor").get("id").in(filtro.getProfessores()));
        }

        countQuery.where(cb.and(countPredicates.toArray(new Predicate[0])));
        Long total = em.createQuery(countQuery).getSingleResult();

        return new PaginatedResponse<>(resultado, total);
    }
}
