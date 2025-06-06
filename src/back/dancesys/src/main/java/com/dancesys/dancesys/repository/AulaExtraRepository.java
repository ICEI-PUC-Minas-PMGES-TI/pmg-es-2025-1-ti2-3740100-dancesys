package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.AulaExtra;
import com.dancesys.dancesys.entity.EnsaioApresentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AulaExtraRepository extends JpaRepository<AulaExtra, Long> {

    List<AulaExtra> findByHorarioInicioLessThanAndHorarioFimGreaterThanAndIdProfessorId(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Long idProfessor);
}