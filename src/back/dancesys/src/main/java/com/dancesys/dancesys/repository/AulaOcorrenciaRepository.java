package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.AulaOcorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AulaOcorrenciaRepository extends JpaRepository<AulaOcorrencia, Long> {
    List<AulaOcorrencia> findByIdAula_IdAndDataGreaterThan(Long idAula, LocalDate data);
    Optional<AulaOcorrencia> findById(Long id);
}
