package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.dto.FigurinoAlunoFilter;
import com.dancesys.dancesys.entity.FigurinoApresentacao;
import com.dancesys.dancesys.infra.PaginatedResponse;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class FigurinoAlunoRepositoryCustom {
    private EntityManager em;

    public FigurinoAlunoRepositoryCustom(EntityManager em) {
        this.em = em;
    }

//    public PaginatedResponse<FigurinoApresentacao> buscar(FigurinoAlunoFilter filtro){
//
//    }
}
