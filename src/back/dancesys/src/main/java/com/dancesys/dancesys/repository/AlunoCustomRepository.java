package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.dto.AlunoFilterDTO;
import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.ModalidadeAlunoNivel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AlunoCustomRepository {
    private final EntityManager em;

    public AlunoCustomRepository(EntityManager em) {
        this.em = em;
    }

    public List<Aluno> buscarAlunos(AlunoFilterDTO filtro) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Aluno> query = cb.createQuery(Aluno.class);
        Root<Aluno> alunoRoot = query.from(Aluno.class);
        Join<Object, Object> usuarioJoin = alunoRoot.join("idUsuario"); // Fazendo o JOIN

        Fetch<Aluno, ModalidadeAlunoNivel> modalidadesFetch = alunoRoot.fetch("modalidades", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
            predicates.add(cb.like(cb.lower(usuarioJoin.get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
        }

        if (filtro.getCpf() != null && !filtro.getCpf().isEmpty()) {
            predicates.add(cb.equal(usuarioJoin.get("cpf"), filtro.getCpf()));
        }

        if (filtro.getEmail() != null && !filtro.getEmail().isEmpty()) {
            predicates.add(cb.like(cb.lower(usuarioJoin.get("email")), "%" + filtro.getEmail().toLowerCase() + "%"));
        }

        if (filtro.getTipo() != null) {
            predicates.add(cb.equal(alunoRoot.get("tipo"), filtro.getTipo()));
        }

        if (filtro.getStatus() != null) {
            predicates.add(cb.equal(usuarioJoin.get("status"), filtro.getStatus()));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0]))).distinct(true);

        TypedQuery<Aluno> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

}
