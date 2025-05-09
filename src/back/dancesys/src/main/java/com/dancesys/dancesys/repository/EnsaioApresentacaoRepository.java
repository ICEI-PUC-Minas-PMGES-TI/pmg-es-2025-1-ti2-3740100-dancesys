package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.EnsaioApresentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnsaioApresentacaoRepository extends JpaRepository<EnsaioApresentacao, Long> {

    EnsaioApresentacao findById(long id);

}