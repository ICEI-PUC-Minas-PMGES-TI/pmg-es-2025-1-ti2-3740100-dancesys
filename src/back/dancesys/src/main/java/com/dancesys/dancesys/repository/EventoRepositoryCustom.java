package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.dto.EventoFilter;
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
public class EventoRepositoryCustom {
    private EntityManager em;

    public EventoRepositoryCustom(EntityManager em) {
        this.em = em;
    }
    public PaginatedResponse<Evento> buscar(EventoFilter filtro){
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Evento> query = cb.createQuery(Evento.class);
        Root<Evento> root = query.from(Evento.class);

        List<Predicate> predicates = new ArrayList<>();

        if(filtro.getNome() != null && !filtro.getNome().equals("")){
            predicates.add(cb.like(root.get("nome"), "%" + filtro.getNome() + "%"));
        }

        if(filtro.getLocal() != null && !filtro.getLocal().equals("")){
            predicates.add(cb.like(root.get("local"), "%" + filtro.getLocal() + "%"));
        }

        if(filtro.getData() != null){
            LocalDate data = filtro.getData();
            LocalDateTime startOfDay = data.atStartOfDay();
            LocalDateTime endOfDay = data.atTime(LocalTime.MAX);

            predicates.add(cb.between(root.get("dataHoraInicio"), startOfDay, endOfDay));
        }

        if(filtro.getAlunos() != null &&  !filtro.getAlunos().isEmpty()){
            predicates.add(root.get("apresentacoes").get("alunos").get("idAluno").get("id").in(filtro.getAlunos()));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        TypedQuery<Evento> typedQuery = em.createQuery(query);

        if (filtro.getTamanho() != null && filtro.getTamanho() > 0) {
            int pagina = filtro.getPagina() != null ? filtro.getPagina() : 0;
            typedQuery.setFirstResult(pagina * filtro.getTamanho());
            typedQuery.setMaxResults(filtro.getTamanho());
        }

        List<Evento> resultado = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Evento> countRoot = countQuery.from(Evento.class);
        countQuery.select(cb.count(countRoot));

        List<Predicate> countPredicates = new ArrayList<>();

        if(filtro.getNome() != null && !filtro.getNome().equals("")){
            countPredicates.add(cb.like(countRoot.get("nome"), "%" + filtro.getNome() + "%"));
        }

        if(filtro.getLocal() != null && !filtro.getLocal().equals("")){
            countPredicates.add(cb.like(countRoot.get("local"), "%" + filtro.getLocal() + "%"));
        }

        if(filtro.getData() != null){
            LocalDate data = filtro.getData();
            LocalDateTime startOfDay = data.atStartOfDay();
            LocalDateTime endOfDay = data.atTime(LocalTime.MAX);

            countPredicates.add(cb.between(countRoot.get("dataHoraInicio"), startOfDay, endOfDay));
        }

        if(filtro.getAlunos() != null &&  !filtro.getAlunos().isEmpty()){
            countPredicates.add(countRoot.get("apresentacoes").get("alunos").get("idAluno").get("id").in(filtro.getAlunos()));
        }

        countQuery.where(cb.and(countPredicates.toArray(new Predicate[0])));
        Long total = em.createQuery(countQuery).getSingleResult();

        return new PaginatedResponse<>(resultado, total);
    }
}
