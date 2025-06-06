package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.AulaOcorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AulaOcorrenciaRepository extends JpaRepository<AulaOcorrencia, Long> {
    List<AulaOcorrencia> findByIdAula_IdAndDataGreaterThan(Long idAulaId, LocalDate dataIsGreaterThan);

    Optional<AulaOcorrencia> findById(Long id);

    List<AulaOcorrencia> findByDataAndIdAula_IdProfessor_IdAndStatus(LocalDate date, Long idProfessor,Integer status);
}
