package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.EnsaioApresentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EnsaioApresentacaoRepository extends JpaRepository<EnsaioApresentacao, Long> {

    EnsaioApresentacao findById(long id);

    boolean existsByIdApresentacaoEventoId(Long id);

    List<EnsaioApresentacao> findByDataHoraInicioLessThanAndDataHoraFimGreaterThanAndIdProfessorId(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Long idProfessor);

}