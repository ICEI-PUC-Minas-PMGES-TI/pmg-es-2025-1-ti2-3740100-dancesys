package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.dto.EnsaioFilter;
import com.dancesys.dancesys.entity.EnsaioApresentacao;
import com.dancesys.dancesys.entity.Evento;
import com.dancesys.dancesys.infra.PaginatedResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EnsaioRepositoryCustom {
    private EntityManager em;

    public EnsaioRepositoryCustom(EntityManager em) {
        this.em = em;
    }

    public PaginatedResponse<EnsaioApresentacao> buscar(EnsaioFilter filtro){
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<EnsaioApresentacao> query = cb.createQuery(EnsaioApresentacao.class);
        Root<EnsaioApresentacao> root = query.from(EnsaioApresentacao.class);

        List<Predicate> predicates = new ArrayList<>();

        if(filtro.getIdProfessor() != null){
            predicates.add(cb.equal(root.get("idProfessor").get("id"), filtro.getIdProfessor()));
        }

        if(filtro.getData() != null){
            LocalDate data = filtro.getData();
            LocalDateTime startOfDay = data.atStartOfDay();
            LocalDateTime endOfDay = data.atTime(LocalTime.MAX);

            predicates.add(cb.between(root.get("dataHoraInicio"), startOfDay, endOfDay));
        }

        if(filtro.getApresentacoes() != null && !filtro.getApresentacoes().isEmpty()){
            predicates.add(root.get("idApresentacaoEvento").get("id").in(filtro.getApresentacoes()));
        }

        if(filtro.getAlunos() != null && !filtro.getAlunos().isEmpty()){
            predicates.add(root.get("idAluno").get("id").in(filtro.getAlunos()));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        TypedQuery<EnsaioApresentacao> typedQuery = em.createQuery(query);

        if (filtro.getTamanho() != null && filtro.getTamanho() > 0) {
            int pagina = filtro.getPagina() != null ? filtro.getPagina() : 0;
            typedQuery.setFirstResult(pagina * filtro.getTamanho());
            typedQuery.setMaxResults(filtro.getTamanho());
        }

        List<EnsaioApresentacao> resultado = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<EnsaioApresentacao> countRoot = countQuery.from(EnsaioApresentacao.class);
        countQuery.select(cb.count(countRoot));

        List<Predicate> countPredicates = new ArrayList<>();

        if(filtro.getIdProfessor() != null){
            countPredicates.add(cb.equal(countRoot.get("idProfessor").get("id"), filtro.getIdProfessor()));
        }

        if(filtro.getData() != null){
            LocalDate data = filtro.getData();
            LocalDateTime startOfDay = data.atStartOfDay();
            LocalDateTime endOfDay = data.atTime(LocalTime.MAX);

            countPredicates.add(cb.between(countRoot.get("dataHoraInicio"), startOfDay, endOfDay));
        }

        if(filtro.getApresentacoes() != null && !filtro.getApresentacoes().isEmpty()){
            countPredicates.add(countRoot.get("idApresentacaoEvento").get("id").in(filtro.getApresentacoes()));
        }

        if(filtro.getAlunos() != null && !filtro.getAlunos().isEmpty()){
            countPredicates.add(countRoot.get("idAluno").get("id").in(filtro.getAlunos()));
        }
        countQuery.where(cb.and(countPredicates.toArray(new Predicate[0])));
        Long total = em.createQuery(countQuery).getSingleResult();

        return new PaginatedResponse<>(resultado, total);
    }
}
