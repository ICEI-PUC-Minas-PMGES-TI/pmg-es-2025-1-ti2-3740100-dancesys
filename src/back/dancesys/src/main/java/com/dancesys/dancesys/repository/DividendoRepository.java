package com.dancesys.dancesys.repository;


import com.dancesys.dancesys.entity.Dividendo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DividendoRepository extends JpaRepository<Dividendo, Long> {
    Optional<Dividendo> findById(Long id);

    List<Dividendo> findByCriadoEmLessThanEqualAndStatusEquals(LocalDate criadoEm, Integer status);

    @Query(value = """
        SELECT
            MONTH(criado_em) AS mes,
            tipo,

            COUNT(CASE 
                WHEN status = 2 AND pago_em IS NOT NULL AND DATEDIFF(DAY, criado_em, pago_em) < 30 THEN 1 
            END) AS boletosPagosSemAtraso,

            SUM(CASE 
                WHEN status = 2 AND pago_em IS NOT NULL AND DATEDIFF(DAY, criado_em, pago_em) < 30 THEN valor 
            END) AS somaValoresSemAtraso,

            COUNT(CASE 
                WHEN status = 2 AND pago_em IS NOT NULL AND DATEDIFF(DAY, criado_em, pago_em) >= 30 THEN 1 
            END) AS boletosPagosComAtraso,

            SUM(CASE 
                WHEN status = 2 AND pago_em IS NOT NULL AND DATEDIFF(DAY, criado_em, pago_em) >= 30 THEN valor 
            END) AS somaValoresComAtraso,

            AVG(CASE 
                WHEN status = 2 AND pago_em IS NOT NULL AND DATEDIFF(DAY, criado_em, pago_em) >= 30 THEN DATEDIFF(DAY, criado_em, pago_em) 
            END) AS mediaDiasAtraso,

            COUNT(CASE 
                WHEN status IN (1, 3) THEN 1 
            END) AS boletosNaoPagos

        FROM
            Dividendo
        WHERE
            YEAR(criado_em) = :ano
        GROUP BY
            MONTH(criado_em), tipo
        ORDER BY
            mes, tipo
    """, nativeQuery = true)
    List<Object[]> buscarRelatorioDividendo(@Param("ano") Integer ano);
}
