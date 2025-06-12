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
            m.mes,
            ISNULL(ao.total_realizadas, 0) AS total_aulas_ocorrentes_realizadas,
            ISNULL(ao.total_canceladas, 0) AS total_aulas_ocorrentes_canceladas,
            ISNULL(ao.minutos, 0) AS minutos_aulas_ocorrentes,
            ISNULL(ae.total_realizadas, 0) AS total_aulas_extras_realizadas,
            ISNULL(ae.total_canceladas, 0) AS total_aulas_extras_canceladas,
            ISNULL(ae.minutos, 0) AS minutos_aulas_extras,
            ISNULL(ax.total, 0) AS total_aulas_experimentais,
            ISNULL(ax.minutos, 0) AS minutos_aulas_experimentais
        FROM (
            SELECT DISTINCT MONTH(mesReferencia) AS mes
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
            ) m
        ) m
        LEFT JOIN (
            SELECT
                MONTH(ao.data) AS mes,
                COUNT(CASE WHEN ao.status = 1 THEN 1 END) AS total_realizadas,
                COUNT(CASE WHEN ao.status = 2 THEN 1 END) AS total_canceladas,
                SUM(CASE WHEN ao.status = 1 THEN DATEDIFF(MINUTE, a.horario_inicio, a.horario_fim) ELSE 0 END) AS minutos
                FROM Aula_Ocorrencia ao
                INNER JOIN Aula a ON ao.id_Aula = a.id
                WHERE a.id_Professor = :idProfessor AND YEAR(ao.data) = :ano
                GROUP BY MONTH(ao.data)
            ) ao ON ao.mes = m.mes
        LEFT JOIN (
            SELECT
                MONTH(horario_inicio) AS mes,
                COUNT(CASE WHEN situacao = 2 THEN 1 END) AS total_realizadas,
                COUNT(CASE WHEN situacao = 4 THEN 1 END) AS total_canceladas,
                SUM(CASE WHEN situacao = 2 THEN DATEDIFF(MINUTE, horario_inicio, horario_fim) ELSE 0 END) AS minutos
                FROM Aula_Extra
                WHERE id_Professor = :idProfessor AND YEAR(horario_inicio) = :ano
                GROUP BY MONTH(horario_inicio)
            ) ae ON ae.mes = m.mes
        LEFT JOIN (
            SELECT
                MONTH(data_horario_inicio) AS mes,
                COUNT(*) AS total,
                SUM(DATEDIFF(MINUTE, data_horario_inicio, data_horario_fim)) AS minutos
                FROM Aula_Experimental
                WHERE id_Professor = :idProfessor AND YEAR(data_horario_inicio) = :ano
                GROUP BY MONTH(data_horario_inicio)
        ) ax ON ax.mes = m.mes
    ORDER BY m.mes;
    """, nativeQuery = true)
    List<Object[]> getRelatrioAulas(@Param("idProfessor") Long idProfessor, @Param("ano") Integer ano);
}
