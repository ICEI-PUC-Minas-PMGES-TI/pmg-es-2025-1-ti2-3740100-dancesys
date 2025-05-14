package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.FigurinoApresentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FigurinoApresentacaoRepository extends JpaRepository<FigurinoApresentacao, Long> {

    FigurinoApresentacao findById(long id);

}