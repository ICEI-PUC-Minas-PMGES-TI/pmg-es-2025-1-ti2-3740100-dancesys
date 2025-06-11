package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.AulaOcorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AulaOcorrenciaRepository extends JpaRepository<AulaOcorrencia, Long>, JpaSpecificationExecutor<AulaOcorrencia> {
    List<AulaOcorrencia> findByIdAula_IdAndDataGreaterThan(Long idAulaId, LocalDate dataIsGreaterThan);

    Optional<AulaOcorrencia> findById(Long id);

    List<AulaOcorrencia> findByDataAndIdAula_IdProfessor_IdAndStatus(LocalDate date, Long idProfessor,Integer status);

    List<AulaOcorrencia> findByIdAulaId(Long idAulaId);

    @Query(value = """
    SELECT
        MONTH(d.mesReferencia) AS mes,
        
        COUNT(CASE WHEN ao.status = 1 THEN 1 END) AS totalAulasOcorrentesRealizadas,
        COUNT(CASE WHEN ao.status = 2 THEN 1 END) AS totalAulasOcorrentesCanceladas,
        SUM(CASE WHEN ao.status = 1 THEN DATEDIFF(MINUTE, ao.horario_inicio, ao.horario_fim) ELSE 0 END) AS minutosAulasOcorrentes,

        COUNT(CASE WHEN ae.situacao = 2 THEN 1 END) AS totalAulasExtrasRealizadas,
        COUNT(CASE WHEN ae.situacao = 4 THEN 1 END) AS totalAulasExtrasCanceladas,
        SUM(CASE WHEN ae.situacao = 2 THEN DATEDIFF(MINUTE, ae.horario_inicio, ae.horario_fim) ELSE 0 END) AS minutosAulasExtras,

        COUNT(ax.id) AS totalAulasExperimentais,
        SUM(DATEDIFF(MINUTE, ax.data_horario_inicio, ax.data_horario_fim)) AS minutosAulasExperimentais

    FROM (
        SELECT DATEFROMPARTS(YEAR(ao.data), MONTH(ao.data), 1) AS mesReferencia
        FROM Aula_Ocorrencia ao
        INNER JOIN Aula a ON ao.id_Aula = a.id
        WHERE a.id_Professor = :idProfessor AND YEAR(ao.data) = :ano

        UNION

        SELECT DATEFROMPARTS(YEAR(ae.horario_inicio), MONTH(ae.horario_inicio), 1)
        FROM Aula_Extra ae
        WHERE ae.id_Professor = :idProfessor AND YEAR(ae.horario_inicio) = :ano

        UNION

        SELECT DATEFROMPARTS(YEAR(ax.data_horario_inicio), MONTH(ax.data_horario_inicio), 1)
        FROM Aula_Experimental ax
        WHERE ax.id_Professor = :idProfessor AND YEAR(ax.data_horario_inicio) = :ano
    ) d

    LEFT JOIN (
        SELECT ao.*, a.horario_inicio, a.horario_fim
        FROM Aula_Ocorrencia ao
        INNER JOIN Aula a ON ao.id_Aula = a.id
        WHERE a.id_Professor = :idProfessor AND YEAR(ao.data) = :ano
    ) ao ON DATEFROMPARTS(YEAR(ao.data), MONTH(ao.data), 1) = d.mesReferencia

    LEFT JOIN (
        SELECT *
        FROM Aula_Extra
        WHERE id_Professor = :idProfessor AND YEAR(horario_inicio) = :ano
    ) ae ON DATEFROMPARTS(YEAR(ae.horario_inicio), MONTH(ae.horario_inicio), 1) = d.mesReferencia

    LEFT JOIN (
        SELECT *
        FROM Aula_Experimental
        WHERE id_Professor = :idProfessor AND YEAR(data_horario_inicio) = :ano
    ) ax ON DATEFROMPARTS(YEAR(ax.data_horario_inicio), MONTH(ax.data_horario_inicio), 1) = d.mesReferencia

    GROUP BY MONTH(d.mesReferencia)
    ORDER BY mes
    """, nativeQuery = true)
    List<Object[]> getRelatrioAulas(@Param("idProfessor") Long idProfessor, @Param("ano") Integer ano);
}
