package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.AulaExperimental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AulaExperimentalRepository extends JpaRepository<AulaExperimental, Long> {
    @Query(value = """
        SELECT
            MONTH(criado_em) AS mes,
            SUM(CASE WHEN situacao = 2 THEN 1 ELSE 0 END) AS total_convertido,
            SUM(CASE WHEN situacao = 3 THEN 1 ELSE 0 END) AS total_recusado,
            COUNT(*) AS total_criadas,
            SUM(CASE WHEN finalizado_em IS NOT NULL AND MONTH(finalizado_em) = MONTH(criado_em) THEN 1 ELSE 0 END) AS total_finalizadas,
            SUM(CASE WHEN motivo = 1 THEN 1 ELSE 0 END) AS total_interesse,
            SUM(CASE WHEN motivo = 2 THEN 1 ELSE 0 END) AS total_financeiro,
            SUM(CASE WHEN motivo = 3 THEN 1 ELSE 0 END) AS total_outro
        FROM aula_experimental
        WHERE YEAR(criado_em) = :ano
        GROUP BY MONTH(criado_em)
        ORDER BY mes
    """, nativeQuery = true)
    List<Object[]> buscarEstatisticasPorAno(@Param("ano") int ano);
}
