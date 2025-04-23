package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.AulaOcorrencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AulaOcorrenciaRepository extends JpaRepository<AulaOcorrencia, Long> {
    List<AulaOcorrencia> findByIdAula_IdAndDataGreaterThan(Long idAulaId, LocalDate dataIsGreaterThan);
}
