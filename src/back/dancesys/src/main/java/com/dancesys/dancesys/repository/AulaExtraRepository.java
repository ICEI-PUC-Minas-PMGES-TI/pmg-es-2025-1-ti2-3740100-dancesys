package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.AulaExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AulaExtraRepository extends JpaRepository<AulaExtra, Long> {

    List<AulaExtra> findByCodigoAndSituacaoAndIdProfessor(String codigo, Integer situacao, Long idProfessor);
}